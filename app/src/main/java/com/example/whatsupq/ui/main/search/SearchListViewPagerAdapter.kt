package com.example.whatsupq.ui.main.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SearchListViewPagerAdapter(fm: FragmentManager, recentList: ArrayList<String>, recommendList: ArrayList<String>) :
    FragmentPagerAdapter(fm) {
    val recent = recentList
    val recommend = recommendList

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> SearchListFragment(recent)
            1 -> SearchListFragment(recommend)
            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "최근 검색어"
            1 -> "추천 검색어"
            else -> null
        }
    }

}
