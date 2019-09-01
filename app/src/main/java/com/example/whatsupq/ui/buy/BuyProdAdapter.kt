package com.example.whatsupq.ui.buy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_buy_prod.view.*

class BuyProdAdapter(val context: Context, val prodList: ArrayList<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_buy_prod, parent, false)
        return ViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return prodList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = prodList[position]
        (holder as ViewHolder).apply{
            bind(item)
        }
    }

    class ViewHolder(val context: Context, val v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item: Int) {
            v.buy_prodinfo_img.setImageResource(item)
        }
    }
}