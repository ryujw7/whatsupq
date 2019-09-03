package com.example.whatsupq

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.whatsupq.ui.tutorial.TutorialActivity

class SplashActivity : BaseActivity() {
    private val SPLASH_TIME_OUT:Long = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, TutorialActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
