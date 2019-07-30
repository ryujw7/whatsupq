package com.example.whatsupq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        tutorial_viewpager.adapter = TutorialViewpagerAdapter(supportFragmentManager)
        tutorial_viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                if(position == 2){
                    tutorial_btn.isEnabled=true
                }
                else{
                    tutorial_btn.isEnabled=false
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        tutorial_indicator.setupWithViewPager(tutorial_viewpager,true)
        tutorial_btn.setOnClickListener {
            intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
