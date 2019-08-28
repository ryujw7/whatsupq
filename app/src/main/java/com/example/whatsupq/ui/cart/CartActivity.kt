package com.example.whatsupq.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : BaseActivity() {

    var category_item_list = mutableMapOf<String, ArrayList<CartItem>>(
        Pair(
            "생필품",
            arrayListOf(
                CartItem("생필품", "mipmap/ic_launcher", "1", 5000),
                CartItem("생필품", "ic_launcher", "2", 50000),
                CartItem("생필품", "mipmap/ic_launcher", "3", 5000),
                CartItem("생필품", "ic_launcher", "4", 50000),
                CartItem("생필품", "mipmap/ic_launcher", "5", 5000),
                CartItem("생필품", "ic_launcher", "6", 50000)
            )
        ), Pair(
            "주방가전",
            arrayListOf(
                CartItem("주방가전", "mipmap/ic_launcher", "7", 5000),
                CartItem("주방가전", "ic_launcher", "8", 50000),
                CartItem("주방가전", "mipmap/ic_launcher", "9", 5000),
                CartItem("주방가전", "ic_launcher", "10", 50000),
                CartItem("주방가전", "mipmap/ic_launcher", "11", 5000),
                CartItem("주방가전", "ic_launcher", "12", 50000),
                CartItem("주방가전", "mipmap/ic_launcher", "13", 5000),
                CartItem("주방가전", "ic_launcher", "14", 50000)
            )
        )
    )

    fun refreshCost() {
        var total = 0
        var isEmpty = true
        for (itemList in category_item_list.values) {
            for (item in itemList) {
                if (item.checked) {
                    total += item.total_cost
                    isEmpty = false
                }
            }

        }
        cart_total_cost.text = total.toString()
        cart_order.isEnabled = !isEmpty
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        back_btn.setOnClickListener {
            finish()
        }
        val cartItemAdapter = CartItemAdapter(this, category_item_list)
        cart_listView.adapter = cartItemAdapter
        cart_checkall.setOnClickListener {
            for (itemList in category_item_list.values) {
                for (item in itemList) {
                    item.checked = cart_checkall.isChecked
                }
            }
            cartItemAdapter.notifyDataSetChanged()
            refreshCost()
        }
    }
}
