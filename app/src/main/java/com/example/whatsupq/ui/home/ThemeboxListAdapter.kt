package com.example.whatsupq.ui.home


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_15,
    R.string.tab_text_16,
    R.string.tab_text_17,
    R.string.tab_text_18,
    R.string.tab_text_19,
    R.string.tab_text_20
)
class ThemeboxListAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HomePlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return ThemeboxListPlaceHolderFragment().newInstance(getPageTitle(position).toString(),"1")
        } else if (position == 1) {
            return ThemeboxListPlaceHolderFragment().newInstance(getPageTitle(position).toString(),"1")
        } else if (position == 2) {
            return ThemeboxListPlaceHolderFragment().newInstance(getPageTitle(position).toString(),"1")
        } else if (position == 3){
            return ThemeboxListPlaceHolderFragment().newInstance(getPageTitle(position).toString(),"1")
        } else if (position == 4){
            return ThemeboxListPlaceHolderFragment().newInstance(getPageTitle(position).toString(),"1")
        } else {
            return ThemeboxListPlaceHolderFragment().newInstance(getPageTitle(position).toString(),"1")
        }
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 6
    }
}