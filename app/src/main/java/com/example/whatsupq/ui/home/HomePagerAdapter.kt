package com.example.whatsupq.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R

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
        // Return a HomePlaceholderFragment (defined as a static inner class below).
        val fragment = when(position) {
            1 -> LivingPlaceholderFragment().newInstance()
            2 -> ThemeboxPlaceholderFragment().newInstance()
            3 -> SpecialPlaceholderFragment().newInstance()
            else -> ChoicePlaceholderFragment().newInstance()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 4
    }
}