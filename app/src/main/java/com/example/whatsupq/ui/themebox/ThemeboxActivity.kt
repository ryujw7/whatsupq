package com.example.whatsupq.ui.themebox

import android.os.Bundle
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_themebox.*

class ThemeboxActivity : BaseActivity() {
    val cost = 21900
    var imgSrcList = arrayListOf(
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themebox)
        themebox_cost.text = cost.toString()
        themebox_viewpager.adapter = ThemeViewpagerAdapter(supportFragmentManager, imgSrcList)
        themebox_indicator.setupWithViewPager(themebox_viewpager, true)
        val bottomSheetDialogFragment = ThemeboxBottomSheetDialogFragment(cost)
        themebox_addcart.setOnClickListener {
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
}
