package com.example.whatsupq.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.whatsupq.R

class ThemeboxItem(val themeImg: String, val themeTitle: String, val themeSubTitle: String)

class ThemeboxItemAdapter(val context: ThemeboxListPlaceHolderFragment, val itemList:ArrayList<ThemeboxItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.fragment_home_themebox_item, null)
        val themeImg = view.findViewById<ImageButton>(R.id.theme_image)
        val themeTitle = view.findViewById<TextView>(R.id.theme_title)
        val themeSubTitle = view.findViewById<TextView>(R.id.theme_subtitle)
        val themeboxItem = itemList[p0]
        val resourceId = context.resources.getIdentifier(themeboxItem.themeImg,"drawable","com.example.whatsupq")
        themeImg.setImageResource(resourceId)
        themeTitle.text = themeboxItem.themeTitle
        themeSubTitle.text = themeboxItem.themeSubTitle
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
