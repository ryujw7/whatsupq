package com.example.whatsupq.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_search_list.view.*

class SearchResultListFragment(val searchList : ArrayList<String>) : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_list, container, false)
        val searchItemAdapter = SearchItemAdapter(searchList)
        view.search_recyclerview.adapter = searchItemAdapter
        return view
    }
}