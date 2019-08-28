package com.example.whatsupq.ui.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_search_item.view.*

class SearchItemAdapter(val searchList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = searchList[position]
        (holder as ViewHolder).apply{
            bind(item)
            itemView.search_delete.setOnClickListener {
                searchList.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }
    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val view : View = v
        fun bind(item : String){
            view.search_item.text = item
        }
    }

}