package com.example.whatsupq

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.living_info_image_item.view.*
class LivingInfoImage(var index : Int, var image : Bitmap)


class LivingItemInfoImageAdapter(val context : LivingItemInfoActivity, val itemList : ArrayList<LivingInfoImage>) : BaseAdapter() {
    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.living_info_image_item, null)
        view.living_image_item.scaleType = ImageView.ScaleType.CENTER_CROP
        view.living_image_item.setImageBitmap(itemList[p0].image)
        return view
    }

    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }

    override fun getCount(): Int {
        return itemList.size
    }

}