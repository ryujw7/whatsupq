package com.example.whatsupq.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    var itemList = arrayListOf( // 해결해야할 문제 : 그래픽버그 씨발거
        CartItem("ic_launcher_background","제주 삼다슈", 13000),
        CartItem("ic_launcher_foreground","제주 사다슈", 14000),
        CartItem("ic_launcher_background","제주 오다슈", 15000),
        CartItem("ic_launcher_background","제주 육다슈", 16000),
        CartItem("ic_launcher_background","제주 칠다슈", 17000),
        CartItem("ic_launcher_background","제주 팔다슈", 13000),
        CartItem("ic_launcher_foreground","제주 구다슈", 14000),
        CartItem("ic_launcher_background","제주 십다슈", 15000),
        CartItem("ic_launcher_background","제주 십일다슈", 13000),
        CartItem("ic_launcher_foreground","제주 십이다슈", 14000),
        CartItem("ic_launcher_background","제주 십삼다슈", 15000),
        CartItem("ic_launcher_background","제주 십사다슈", 13000),
        CartItem("ic_launcher_foreground","제주 십오다슈", 14000),
        CartItem("ic_launcher_background","제주 십육다슈", 15000)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartItemAdapter = CartItemAdapter(this, itemList)
        cart_listView.adapter = cartItemAdapter
        cart_checkall.setOnClickListener {
            for(item in itemList){
                item.checked = cart_checkall.isChecked
            }
            cartItemAdapter.notifyDataSetChanged()
        }
    }
}
