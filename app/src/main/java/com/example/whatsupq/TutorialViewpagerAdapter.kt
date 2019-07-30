package com.example.whatsupq

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TutorialViewpagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> TutorialAFragment()
            1 -> TutorialBFragment()
            2 -> TutorialCFragment()
            else -> null
        }
    }

    override fun getCount() = 3

}