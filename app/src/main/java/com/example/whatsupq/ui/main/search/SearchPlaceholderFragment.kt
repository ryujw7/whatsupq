package com.example.whatsupq.ui.main.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.whatsupq.MainActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SearchPlaceholderFragment : Fragment(), MainActivity.OnKeyBackPressedListener {

    var recentList = arrayListOf("최근", "검색", "어어", "어어", "아앙") // 테스트 용으로 우겨넣은 리스트
    var recommendList = arrayListOf("추천", "검색", "어어", "으어", "어어")
    lateinit var root : View
    lateinit var mainActivity : MainActivity
    fun newInstance(): SearchPlaceholderFragment {
        val args = Bundle()

        val frag = SearchPlaceholderFragment()
        frag.arguments = args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_search, container, false)

        val fragmentAdapter = SearchListViewPagerAdapter(childFragmentManager, recentList, recommendList)
        root.search_viewpager.adapter = fragmentAdapter
        root.search_bar.setOnClickListener {
            root.search_bar.isIconified = false
        }
        root.search_bar.setOnSearchClickListener {
            root.search_tab.visibility = View.GONE
            root.search_viewpager.visibility = View.GONE
            (context as MainActivity).setOnBackKeyPressedListener(this)
        }
        root.search_bar.setOnCloseListener {
            root.search_tab.visibility = View.VISIBLE
            root.search_viewpager.visibility = View.VISIBLE
            false
        }
        root.search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
        root.search_tab.setupWithViewPager(root.search_viewpager)
        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as MainActivity).setOnBackKeyPressedListener(this)
    }
    override fun onBackKey() {
        mainActivity = activity as MainActivity
        if(!root.search_bar.isIconified) {
            root.search_bar.isIconified = true
        } else {
            (context as MainActivity).setOnBackKeyPressedListener(null)
            mainActivity.onBackPressed()
        }
    }
}