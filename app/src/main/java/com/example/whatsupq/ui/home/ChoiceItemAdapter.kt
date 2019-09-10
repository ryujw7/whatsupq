package com.example.whatsupq.ui.home


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.LivingItemInfoActivity
import com.example.whatsupq.R
import com.example.whatsupq.ui.home.ChoiceItemAdapter.Holder

class ChoiceItem(var index : Int, var id : String, val thumbnailImg: Bitmap)


class ChoiceItemAdapter(val context : Context, val itemList: ArrayList<ChoiceItem>) :
    RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_choiceall_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        (holder).apply{
            bind(item)
            holder.thumbnail.setOnClickListener {
                val intent = Intent(context, LivingItemInfoActivity::class.java)
                intent.putExtra("product_id", itemList[position].id)
                context.startActivity(intent)
            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageButton = itemView.findViewById(R.id.image_thumbnail)
        fun bind(item: ChoiceItem) {
            thumbnail.scaleType = ImageView.ScaleType.FIT_XY
            if(item.thumbnailImg != null) {
                thumbnail.setImageBitmap(item.thumbnailImg)
            } else {
                thumbnail?.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }
}
