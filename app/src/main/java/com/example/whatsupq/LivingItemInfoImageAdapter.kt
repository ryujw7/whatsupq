package com.example.whatsupq

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsupq.LivingItemInfoImageAdapter.LivingItemHolder

class LivingInfoImage(var index: Int, var image: String)


class LivingItemInfoImageAdapter(val context: LivingItemInfoActivity, val itemList: ArrayList<LivingInfoImage>) :
    RecyclerView.Adapter<LivingItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivingItemHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.living_info_image_item, parent, false)
        return LivingItemHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: LivingItemHolder, position: Int) {
        val item = itemList[position]
        (holder).apply {
            bind(item)
        }
    }

    inner class LivingItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItem = itemView.findViewById<ImageView>(R.id.living_image_item)
        fun bind(item: LivingInfoImage) {
            imgItem.scaleType = ImageView.ScaleType.FIT_XY
            Glide.with(context).load(item.image).into(imgItem)
        }
    }
}