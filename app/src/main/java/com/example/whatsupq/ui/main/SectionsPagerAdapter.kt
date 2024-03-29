package com.example.whatsupq.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R
import com.example.whatsupq.ui.curation.CurationPlaceholderFragment
import com.example.whatsupq.ui.main.search.SearchPlaceholderFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HomePlaceholderFragment (defined as a static inner class below).
       val fragment = when(position) {
           1 -> SearchPlaceholderFragment().newInstance()
           2 -> CurationPlaceholderFragment().newInstance()
           3 -> MyPageFragment().newInstance()
           else -> HomePlaceholderFragment().newInstance()
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