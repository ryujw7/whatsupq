package com.example.whatsupq.ui.cart

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart_category.view.*
import kotlinx.android.synthetic.main.activity_cart_item.view.*

class CartItemAdapter(val context: Context, val category_item_list: MutableMap<String, ArrayList<CartItem>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_CATEGORY = 0
    val VIEW_TYPE_ITEM = 1
    var categoryList = category_item_list.keys.toTypedArray()
    var category_position = arrayListOf<Int>()

    init {
        calculateCategoryPosition()
    }

    fun refreshCategory() {
        for(category in categoryList) {
            if(category_item_list.getValue(category).size == 0) {
                category_item_list.remove(category)
            }
        }
        categoryList = category_item_list.keys.toTypedArray()
    }

    fun calculateCategoryPosition() {
        var tempIndex = 0
        category_position.clear()
        for (itemList in category_item_list.values) {
            category_position.add(tempIndex)
            tempIndex += itemList.size + 1
        }
    }

    fun isCategoryPosition(position: Int): Boolean {
        return position in category_position
    }

    fun positionForCategory(position: Int): Int {
        return category_position.indexOf(position)
    }

    fun getItemCategoryPosition(position: Int): Int {
        for (c_index in category_position.indices) {
            if (category_position[c_index] > position) {
                return c_index - 1
            }
        }
        return category_position.size - 1
    }

    fun positionForItem(position: Int): Int {
        for (c_index in category_position.indices) {
            if (category_position[c_index] > position) {
                return position - category_position[c_index - 1] - 1
            }
        }
        return position - category_position.last() - 1
    }

    override fun getItemViewType(position: Int): Int { // 1개 이상의 아이템이 있다고 가정함. 없으면 애초에 이 페이지로 안 옴
        if (isCategoryPosition(position)) return VIEW_TYPE_CATEGORY
        else return VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        var size = 0
        for (itemList in category_item_list.values) {
            size += itemList.size
        }
        return size + category_item_list.keys.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_CATEGORY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_category, parent, false)
                return ViewHolder_category(context, view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_item, parent, false)
                return ViewHolder_item(context, view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder_category) {
            var item = categoryList[positionForCategory(position)]
            holder.apply {
                bind(item)
            }
        } else if (holder is ViewHolder_item) {
            var categoryIndex = getItemCategoryPosition(position)
            var key = categoryList[categoryIndex]
            var index = positionForItem(position)
            var item = category_item_list[key]!![index]
            holder.apply {
                bind(item)
                itemView.tag = item
                itemView.cart_item_delete.setOnClickListener {
                    category_item_list[categoryList[categoryIndex]]!!.removeAt(positionForItem(position))
                    refreshCategory()
                    calculateCategoryPosition()
                    notifyDataSetChanged()
                    (context as CartActivity).refreshCost()
                }
                itemView.cart_item_plus.setOnClickListener {
                    item.amount++
                    item.total_cost = item.cost * item.amount
                    notifyDataSetChanged()
                    (context as CartActivity).refreshCost()
                }
                itemView.cart_item_minus.setOnClickListener {
                    item.amount--
                    item.total_cost = item.cost * item.amount
                    notifyDataSetChanged()
                    (context as CartActivity).refreshCost()
                }
            }
        }

    }

    class ViewHolder_category(val context: Context, v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: String) {
            view.cart_category.text = item
        }
    }

    class ViewHolder_item(val context: Context, v: View) : RecyclerView.ViewHolder(v) {
        val MAXAMOUNT = 10
        private var view: View = v
        fun bind(item: CartItem) {
            val act = context as Activity
            view.cart_item_cost.text = item.total_cost.toString()
            view.cart_item_check.text = item.name
            val resId = context.resources.getIdentifier(item.imgSrc, "drawable", context.packageName)
            view.cart_item_img.setImageResource(resId)
            view.cart_item_plus.isEnabled = (item.amount < MAXAMOUNT)
            view.cart_item_plus.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    return
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when (p0.toString().toInt()) {
                        1 -> itemView.cart_item_minus.isEnabled = false
                        MAXAMOUNT -> itemView.cart_item_plus.isEnabled = false
                        else -> {
                            itemView.cart_item_plus.isEnabled = true
                            itemView.cart_item_minus.isEnabled = true
                        }
                    }
                    return
                }

                override fun afterTextChanged(p0: Editable?) {
                    return
                }
            })
            view.cart_item_minus.isEnabled = (item.amount > 1)
            view.cart_item_amount.text = item.amount.toString()
            view.cart_item_check.setOnCheckedChangeListener(null)
            view.cart_item_check.isChecked = item.checked
            view.cart_item_check.setOnCheckedChangeListener { _, b ->
                item.checked = b
                if (!b) {
                    act.cart_checkall.isChecked = false
                }
                (context as CartActivity).refreshCost()
            }

        }
    }
}