package com.example.whatsupq.ui.themebox

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlin.math.ceil

class ThemeViewpagerAdapter(fm: FragmentManager, val imgSrcList: ArrayList<Int>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val tmpList = ArrayList<Int>()
        tmpList.add(imgSrcList[2 * position])
        if ((2 * position + 1) in imgSrcList.indices) {
            tmpList.add(imgSrcList[2 * position + 1])
        }
        return ThemeboxFragment(tmpList)
    }

    override fun getCount(): Int {
        return ceil(imgSrcList.size / 2.0).toInt()
    }

}
