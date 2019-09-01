package com.example.whatsupq.ui.buy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_buy_payment_card.view.*

class BuyCardAdapter(val context: Context, val list: ArrayList<CardInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_buy_payment_card, parent, false)
        return ViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list[position]
        (holder as ViewHolder).apply{
            bind(item)
        }
    }

    class ViewHolder(val context: Context, val v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item: CardInfo) {
            v.buy_payment_card_name.text = item.cardName
            v.buy_payment_card_num1.text = item.num_1.toString()
            v.buy_payment_card_num2.text = item.num_2.toString()
        }
    }
}