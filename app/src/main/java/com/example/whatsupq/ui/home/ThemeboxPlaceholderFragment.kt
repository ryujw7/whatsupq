package com.example.whatsupq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import com.google.android.material.tabs.TabLayout

class ThemeboxPlaceholderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_themebox, container, false)
        val tabs_themebox:TabLayout = root.findViewById(R.id.tabs_themebox)
        val viewPager: SwipeViewPager = root.findViewById(R.id.view_pager_themebox)
        val themeboxListAdapter = ThemeboxListAdapter(root.context, childFragmentManager)
        viewPager.adapter = themeboxListAdapter
        viewPager.setPagingEnabled(true)
        tabs_themebox.setupWithViewPager(viewPager)
        tabs_themebox.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_BOTTOM)
        tabs_themebox.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!.applicationContext, R.color.lightblue))
        return root
    }

    fun newInstance(): ThemeboxPlaceholderFragment
    {
        val args = Bundle()

        val frag = ThemeboxPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}