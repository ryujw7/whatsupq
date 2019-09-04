package com.example.whatsupq.ui.cart

import android.content.Intent
import android.os.Bundle
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.DB.CartDBHelper
import com.example.whatsupq.R
import com.example.whatsupq.ui.buy.BuyActivity
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : BaseActivity() {

    lateinit var cartDBHelper: CartDBHelper
    var category_item_list = mutableMapOf<String, ArrayList<CartItem>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartDBHelper = CartDBHelper(this, null)

        var cursor = cartDBHelper.getCartProduct("CART_ESSENTIAL")
        if (!cursor!!.isAfterLast) {
            if (category_item_list.get("생필품") == null) {
                category_item_list.put("생필품", arrayListOf())
            }
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val product_id = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                val name = cursor.getString(cursor.getColumnIndex("NAME"))
                val cost = cursor.getInt(cursor.getColumnIndex("PRICE"))
                val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                val frequency = cursor.getInt(cursor.getColumnIndex("FREQUENCY"))
                category_item_list.get("생필품")!!.add(CartItem(product_id, name, cost, amount, frequency))
                cursor.moveToNext()
            }
        }
        cursor = cartDBHelper.getCartProduct("CART_BOX_THEME")
        if (!cursor!!.isAfterLast) {
            if (category_item_list.get("테마박스") == null) {
                category_item_list.put("테마박스", arrayListOf())
            }
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val product_id = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                val name = cursor.getString(cursor.getColumnIndex("NAME"))
                val cost = cursor.getInt(cursor.getColumnIndex("PRICE"))
                val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                val frequency = cursor.getInt(cursor.getColumnIndex("FREQUENCY"))
                category_item_list.get("테마박스")!!.add(CartItem(product_id, name, cost, amount, frequency))
                cursor.moveToNext()
            }
        }

        back_btn.setOnClickListener {
            finish()
        }
        val cartItemAdapter = CartItemAdapter(this, category_item_list, cartDBHelper)
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
        cart_order.setOnClickListener {
            startActivity(Intent(this, BuyActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

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
}
