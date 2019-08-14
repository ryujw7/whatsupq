package com.example.whatsupq.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.whatsupq.R

class SpecialItem(val specialImg: String)

class SpecialItemAdapter(val context: SpecialPlaceholderFragment, val itemList:ArrayList<SpecialItem>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context.context).inflate(R.layout.fragment_home_special_item, null)
        val specialImg = view.findViewById<ImageButton>(R.id.special_image)
        val specialItem = itemList[p0]
        val resourceId = context.resources.getIdentifier(specialItem.specialImg,"drawable","com.example.whatsupq")
        specialImg.setImageResource(resourceId)
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
