package com.example.whatsupq.ui.home

import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R


class HomeBannerItemAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val resIdList = arrayListOf<Bitmap>()
    override fun getItem(position: Int): Fragment {
        return HomeBannerItemFragment(resIdList[position])
    }

    override fun getCount(): Int {
        return resIdList.size
    }
}