package com.example.whatsupq.ui.home

import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class BannerList(var index: Int, var redId: Bitmap)

class HomeBannerItemAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var resIdList = ArrayList<BannerList>()
    fun selecter(b: BannerList): Int = b.index
    fun sortList() {
        resIdList.sortBy { selecter(it) }
    }

    override fun getItem(position: Int): Fragment {
        return HomeBannerItemFragment(resIdList[position].index, resIdList[position].redId)
    }

    override fun getCount(): Int {
        return resIdList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}