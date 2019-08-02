package com.example.whatsupq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_tutorial.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        find_id_btn.setOnClickListener {
            startActivity(Intent(this, FindIDActivity::class.java))
        }
        find_pw_btn.setOnClickListener {
            startActivity(Intent(this, FindPWActivity::class.java))
        }
        find_register_btn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
