package com.example.whatsupq

import android.os.Bundle
import com.example.whatsupq.ui.cart.CartActivity
import kotlinx.android.synthetic.main.activity_result_trade.*

class ResultTradeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_trade)
        search_order_history.setOnClickListener {

        }
        home_btn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}