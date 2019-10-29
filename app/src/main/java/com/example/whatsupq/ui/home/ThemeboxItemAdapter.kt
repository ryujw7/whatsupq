package com.example.whatsupq.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.whatsupq.R
import com.example.whatsupq.ui.themebox.ThemeboxActivity
import com.makeramen.roundedimageview.RoundedImageView

class ThemeboxItem(var index : Int, var id : String, var name : String, var content : String, val themeImg: String)

class ThemeboxItemAdapter(val context: ThemeboxListPlaceHolderFragment, val itemList:ArrayList<ThemeboxItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.fragment_home_themebox_item, null)
        val themeImg = view.findViewById<RoundedImageView>(R.id.theme_image)
        val themeTitle = view.findViewById<TextView>(R.id.theme_title)
        val themeSubTitle = view.findViewById<TextView>(R.id.theme_subtitle)
        val themeboxItem = itemList[p0]
        themeboxItem.id = itemList[p0].id
        themeboxItem.index = itemList[p0].index
        if(itemList[p0].name.length > 20) {
            itemList[p0].name = itemList[p0].name.substring(0,15) + "\n" + itemList[p0].name.substring(15,itemList[p0].name.length)
        }
        themeTitle.text = itemList[p0].name
        if(itemList[p0].content.length > 25) {
            itemList[p0].content = itemList[p0].content.substring(0,20) + "\n" + itemList[p0].content.substring(20,itemList[p0].content.length)
        }
        themeSubTitle.text = itemList[p0].content
        Glide.with(this.context).load(itemList[p0].themeImg).into(themeImg)
        view.setOnClickListener {
            val intent = Intent(context.context,ThemeboxActivity::class.java)
            intent.putExtra("themebox_id" , themeboxItem.id)
            context.startActivity(intent)
        }
        return view
    }

    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return itemList.size
    }


}
