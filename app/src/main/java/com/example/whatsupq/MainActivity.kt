package com.example.whatsupq

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.google.android.material.tabs.TabLayout
import com.example.whatsupq.DB.CartDBHelper
import com.example.whatsupq.ui.cart.CartActivity
import com.example.whatsupq.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    lateinit var cartDBHelper: CartDBHelper
    private var monKeyBackPressedListener : OnKeyBackPressedListener? = null
    lateinit var tabs : TabLayout
    lateinit var viewPager : SwipeViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 장바구니 DB 생성
        cartDBHelper = CartDBHelper(this, null)
        // 장바구니 DB 생성 끝

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        val mainBanner: ImageButton = findViewById(R.id.main_banner)
        viewPager.setPagingEnabled(false)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = sectionsPagerAdapter
        tabs = findViewById(R.id.tabs_bottom)
        val back: ImageButton = findViewById(R.id.back_btn)
        back.setVisibility(View.INVISIBLE)
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)?.setIcon(R.drawable.tab_bottom_1)
        tabs.getTabAt(1)?.setIcon(R.drawable.tab_bottom_2)
        tabs.getTabAt(2)?.setIcon(R.drawable.tab_bottom_3)
        tabs.getTabAt(3)?.setIcon(R.drawable.tab_bottom_4)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 == tabs.getTabAt(0)) {

                }
            }

        })
        cart_btn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            mainBanner.setOnClickListener {
                if (actList != null) {
                    actList.clear()
                }
            }
        }
        back_btn.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        if(monKeyBackPressedListener != null) {
            monKeyBackPressedListener!!.onBackKey()
        } else {
            if(tabs.selectedTabPosition != 0) {
                viewPager.currentItem = 0
            } else {
                super.onBackPressed()
            }
        }
    }
    fun setOnBackKeyPressedListener(onKeyBackPressedListener: OnKeyBackPressedListener?) {
        monKeyBackPressedListener = onKeyBackPressedListener
    }
    interface OnKeyBackPressedListener {
        fun onBackKey()
    }
}