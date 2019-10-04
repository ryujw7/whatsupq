package com.example.whatsupq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_tutorial.*

class FindIDActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id)
        back_btn.setOnClickListener {
            finish()
        }
    }
}
