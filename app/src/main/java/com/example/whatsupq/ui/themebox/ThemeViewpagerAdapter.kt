package com.example.whatsupq.ui.themebox

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.LivingInfoImage

class ThemeViewpagerAdapter(fm: FragmentManager, val imgSrcList: ArrayList<LivingInfoImage>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val tmpList = arrayListOf(imgSrcList[position])
        return ThemeboxFragment(tmpList)
    }

    override fun getCount(): Int {
        return imgSrcList.size
    }

}
