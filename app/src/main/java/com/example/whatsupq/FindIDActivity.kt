package com.example.whatsupq

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class FindIDActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id)
        back_btn.setOnClickListener {
            finish()
        }
    }
}
