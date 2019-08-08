package com.example.whatsupq.ui.cart

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart_item.view.*

class CartItemAdapter(val context: Context, val itemList: ArrayList<CartItem>) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    override fun getItemCount() = itemList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_item, parent, false)
        return ViewHolder(context, inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.apply {
            bind(item)
            itemView.tag = item
            itemView.cart_item_delete.setOnClickListener {
                itemList.removeAt(position)
                notifyDataSetChanged()
            }
        }

    }

    class ViewHolder(val context: Context, v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: CartItem) {
            val act = context as Activity
            view.cart_item_cost.text = item.cost.toString()
            view.cart_item_check.text = item.name
            val resId = context.resources.getIdentifier(item.imgSrc, "drawable", context.packageName)
            view.cart_item_img.setImageResource(resId)
            view.cart_item_check.setOnCheckedChangeListener(null)
            view.cart_item_check.isChecked = item.checked
            view.cart_item_check.setOnCheckedChangeListener { compoundButton, b ->
                item.checked = b
                if (!b) {
                    act.cart_checkall.isChecked = false
                }
            }

        }
    }
}