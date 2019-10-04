package com.example.whatsupq

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import com.example.whatsupq.ui.tutorial.TutorialActivity

// Splash를 쓰는 이유와 현재 로딩되는게 맞지 않는 문제인데 Splash가 실행되는 동안 백그라운드에서는 Main에 대한 로딩을 시작해야하는게 맞는듯.


class SplashActivity : BaseActivity() {
    private val SPLASH_TIME_OUT:Long = 1500
    lateinit var tutorialChecker: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0,0)
            tutorialChecker = getSharedPreferences("INIT", Context.MODE_PRIVATE)
            if(tutorialChecker.getBoolean("INIT", true)) {
                startActivity(Intent(this, TutorialActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            else{
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }, SPLASH_TIME_OUT)
    }
}
