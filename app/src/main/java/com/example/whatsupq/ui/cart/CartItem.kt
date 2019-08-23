package com.example.whatsupq.ui.cart

class CartItem (val category : String ,val imgSrc : String, val name : String, val cost : Int, var total_cost : Int = cost, var checked : Boolean = false, var amount : Int = 1)