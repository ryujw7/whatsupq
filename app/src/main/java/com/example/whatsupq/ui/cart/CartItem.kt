package com.example.whatsupq.ui.cart

class CartItem(
    val product_id : String,
    val imgSrc: String,
    val name: String,
    val cost: Int,
    var amount: Int = 1,
    var frequency : Int = 1,
    var total_cost: Int = cost,
    var checked: Boolean = false
)
// 카테고리쪽 지워봤는데 어플 터지면 돌려놓자