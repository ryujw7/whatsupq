package com.example.whatsupq.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.whatsupq.ListAdapter
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import com.example.whatsupq.ui.home.HomePagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_search.*

/**
 * A placeholder fragment containing a simple view.
 */
class SearchPlaceholderFragment : Fragment() {
    var searchList = arrayListOf<String>("콘돔","라면","생수","콜라","세제") // 테스트 용으로 우겨넣은 리스트
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_search, container,false)
        val listAdapter = ListAdapter(root.context, searchList)
        val searchListView:ListView = root.findViewById(R.id.searchListView)
        val search_bar:SearchView = root.findViewById(R.id.search_bar)
        searchListView.adapter = listAdapter
        search_bar.setOnClickListener {
            search_bar.isIconified = false
        }
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