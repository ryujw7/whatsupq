package com.example.whatsupq.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SearchPlaceholderFragment : Fragment() {
    var recentList = arrayListOf("최근","검색","어어","어어","아앙") // 테스트 용으로 우겨넣은 리스트
    var recommendList = arrayListOf("추천","검색","어어","으어","어어")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container,false)

        val fragmentAdapter = SearchListViewPagerAdapter(childFragmentManager, recentList, recommendList)
        root.search_viewpager.adapter = fragmentAdapter
        root.search_bar.setOnClickListener {
            root.search_bar.isIconified = false
        }
        root.search_tab.setupWithViewPager(root.search_viewpager)

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): SearchPlaceholderFragment {
            return SearchPlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}