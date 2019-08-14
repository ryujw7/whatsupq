package com.example.whatsupq

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsupq.ui.cart.CartActivity
import com.example.whatsupq.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: SwipeViewPager = findViewById(R.id.view_pager)
        val mainBanner: ImageButton = findViewById(R.id.main_banner)
        viewPager.setPagingEnabled(false)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs_bottom)
        val back: ImageButton = findViewById(R.id.back_btn)
        back.setVisibility(View.INVISIBLE)
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)?.setIcon(R.drawable.tab_bottom_1)
        tabs.getTabAt(1)?.setIcon(R.drawable.tab_bottom_2)
        tabs.getTabAt(2)?.setIcon(R.drawable.tab_bottom_3)
        tabs.getTabAt(3)?.setIcon(R.drawable.tab_bottom_4)
        cart_btn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            mainBanner.setOnClickListener {
                if (actList != null) {
                    actList.clear()
                }
            }
        }
    }
}