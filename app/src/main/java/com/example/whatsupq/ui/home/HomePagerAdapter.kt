package com.example.whatsupq.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R
import com.example.whatsupq.ui.main.PlaceholderFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_5,
    R.string.tab_text_6,
    R.string.tab_text_7,
    R.string.tab_text_8
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class HomePagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return ChoicePlaceholderFragment.newInstance(position + 1)
        } else if(position == 1) {
            return LivingPlaceholderFragment.newInstance(position + 1)
        } else if(position == 2) {
            return ThemeboxPlaceholderFragment.newInstance(position + 1)
        } else {
            return SpecialPlaceholderFragment.newInstance(position + 1)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}