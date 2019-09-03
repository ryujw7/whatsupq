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
                // 아래 내용들은 product_id 조회해서 넣을 것, 아마
                val imgSrc = "mipmap/ic_launcher"
                val name = "상품명"
                val cost = 1200
                val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                category_item_list.get("생필품")!!.add(
                    CartItem(
                        product_id,
                        imgSrc,
                        name,
                        cost,
                        amount
                    )
                )
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
