package com.example.whatsupq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class SearchListAdapter(val context: Context, val searchList: ArrayList<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.search_item, null)
        val itemText = view.findViewById<TextView>(R.id.search_item)
        val btn = view.findViewById<ImageButton>(R.id.deleteBtn)

        itemText.text = searchList[position]
        btn.setOnClickListener {
            searchList.removeAt(position)
            this.notifyDataSetChanged()
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return searchList[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return searchList.size
    }

}