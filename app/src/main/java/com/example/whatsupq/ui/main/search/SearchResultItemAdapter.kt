package com.example.whatsupq.ui.main.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.LivingItemInfoActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_search_item.view.*
import kotlinx.android.synthetic.main.fragment_search_result_item.view.*

class SearchResultItem ( var name : String, var content : String, var id : String)

class SearchResultItemAdapter(val searchList: ArrayList<SearchResultItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_result_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = searchList[position]
        (holder as ViewHolder).apply{
            bind(item)
        }
    }
    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val view : View = v
        fun bind(item : SearchResultItem){
            var resultItem : String
            resultItem = "[" + item.name + "] " + item.content
            view.search_result_item.text = resultItem
            view.setOnClickListener {
                var intent = Intent(view.context, LivingItemInfoActivity::class.java)
                intent.putExtra("product_id",item.id)
                view.context.startActivity(intent)
            }
        }
    }

}