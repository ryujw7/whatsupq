package com.example.whatsupq.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import com.example.whatsupq.ui.home.HomePagerAdapter
import com.google.android.material.tabs.TabLayout

/**
 * A placeholder fragment containing a simple view.
 */
class HomePlaceholderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container,false)
        val homePagerAdapter = HomePagerAdapter(root.context, childFragmentManager)
        val viewPager: SwipeViewPager = root.findViewById(R.id.view_pager_home)
        viewPager.setPagingEnabled(true)
        viewPager.adapter = homePagerAdapter
        val tabs_home: TabLayout = root.findViewById(R.id.tabs)
        tabs_home.setupWithViewPager(viewPager)
        return root
    }

    fun newInstance(): HomePlaceholderFragment
    {
        val args = Bundle()

        val frag = HomePlaceholderFragment()
        frag.arguments = args

        return frag
    }
}