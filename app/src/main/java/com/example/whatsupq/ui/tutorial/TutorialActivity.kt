package com.example.whatsupq.ui.tutorial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_tutorial.*

lateinit var tutorialChecker: SharedPreferences

class TutorialActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        tutorialChecker = getSharedPreferences("INIT", Context.MODE_PRIVATE)
        tutorial_viewpager.adapter = TutorialViewpagerAdapter(supportFragmentManager)
        tutorial_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                tutorial_btn.isEnabled = (position == 2)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        tutorial_indicator.setupWithViewPager(tutorial_viewpager, true)
        tutorial_btn.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            tutorialChecker.edit().putBoolean("INIT", false).apply()
        }
    }
}
