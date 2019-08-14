package com.example.whatsupq.ui.tutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        tutorial_viewpager.adapter = TutorialViewpagerAdapter(supportFragmentManager)
        tutorial_viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                tutorial_btn.isEnabled = (position == 2)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        tutorial_indicator.setupWithViewPager(tutorial_viewpager,true)
        tutorial_btn.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
