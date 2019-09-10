package com.example.whatsupq.ui.themebox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.LivingInfoImage
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_themebox_list.view.*

class ThemeboxFragment(val imgSrcList: ArrayList<LivingInfoImage>) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_themebox_list, container, false)
        val themeboxItemAdapter = ThemeboxItemAdapter(imgSrcList)
        sortList()
        view.themebox_recyclerview.adapter = themeboxItemAdapter
        themeboxItemAdapter.notifyDataSetChanged()
        return view
    }

    fun selector(livingInfoImage: LivingInfoImage): Int = livingInfoImage.index
    fun sortList() {
        imgSrcList.sortBy { selector(it) }
    }
}