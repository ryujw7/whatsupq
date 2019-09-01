package com.example.whatsupq.ui.themebox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_themebox_item.view.*

class ThemeboxItemAdapter(val themeboxList: ArrayList<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_themebox_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = themeboxList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = themeboxList[position]
        (holder as ViewHolder).apply {
            bind(item)
        }

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view: View = v
        fun bind(item: Int) {
            view.themebox_thumbnail.setImageResource(item)
        }
    }

}
