package com.example.whatsupq.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_search_list.view.*
import kotlinx.android.synthetic.main.fragment_search_result_yes.view.*

class SearchResultListFragment(val searchList : ArrayList<SearchResultItem>) : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_result_yes, container, false)
        val searchItemAdapter = SearchResultItemAdapter(searchList)
        view.search_result_recyclerview.adapter = searchItemAdapter
        return view
    }
}