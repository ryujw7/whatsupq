package com.example.whatsupq.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.whatsupq.R
import com.example.whatsupq.ui.themebox.ThemeboxActivity
import com.makeramen.roundedimageview.RoundedImageView

class ThemeboxItem(val themeImg: Bitmap, val themeTitle: String, val themeSubTitle: String)

class ThemeboxItemAdapter(val context: ThemeboxListPlaceHolderFragment, val itemList:ArrayList<ThemeboxItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.fragment_home_themebox_item, null)
        val themeImg = view.findViewById<RoundedImageView>(R.id.theme_image)
        val themeTitle = view.findViewById<TextView>(R.id.theme_title)
        val themeSubTitle = view.findViewById<TextView>(R.id.theme_subtitle)
        val themeboxItem = itemList[p0]
        themeImg.setImageBitmap(themeboxItem.themeImg)
        themeTitle.text = themeboxItem.themeTitle
        themeSubTitle.text = themeboxItem.themeSubTitle
        view.setOnClickListener {
            val intent = Intent(context.context,ThemeboxActivity::class.java)
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
