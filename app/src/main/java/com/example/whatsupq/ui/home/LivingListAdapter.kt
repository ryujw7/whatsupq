package com.example.whatsupq.ui.home


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_9,
    R.string.tab_text_10,
    R.string.tab_text_11,
    R.string.tab_text_12,
    R.string.tab_text_13,
    R.string.tab_text_14,
    R.string.tab_text_21
)
class LivingListAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HomePlaceholderFragment (defined as a static inner class below).
        return LivingListPlaceholderFragment().newInstance(getPageTitle(position).toString(),"1")
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 7
    }
}