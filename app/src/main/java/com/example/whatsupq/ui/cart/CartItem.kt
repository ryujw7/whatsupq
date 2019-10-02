package com.example.whatsupq.ui.cart

class CartItem(
    val product_id : String,
    val name: String,
    val cost: Int,
    var category : String,
    var amount: Int,
    var frequency : Int,
    var total_cost: Int = cost * amount,
    var checked: Boolean = false
)
// 카테고리쪽 지워봤는데 어플 터지면 돌려놓자