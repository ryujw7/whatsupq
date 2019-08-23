package com.example.whatsupq.ui.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import com.example.whatsupq.ui.home.ChoiceItemAdapter.Holder

class ChoiceItem(val thumbnailImg: String)


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
        holder.bind(itemList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.findViewById<ImageButton>(R.id.image_thumbnail)
        fun bind(item: ChoiceItem) {
            if (item.thumbnailImg != "") {
                val resourceId = itemView.resources.getIdentifier(item.thumbnailImg, "drawable", "com.example.whatsupq")
                thumbnail.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                thumbnail.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }
}
