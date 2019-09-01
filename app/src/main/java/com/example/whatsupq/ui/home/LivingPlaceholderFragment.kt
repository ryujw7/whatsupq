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

class LivingPlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_living, container, false)
        val tabs_living:TabLayout = root.findViewById(R.id.tabs_living)
        val viewPager: SwipeViewPager = root.findViewById(R.id.view_pager_living)
        val livingListAdapter = LivingListAdapter(root.context, childFragmentManager)
        viewPager.adapter = livingListAdapter
        viewPager.setPagingEnabled(true)
        tabs_living.setupWithViewPager(viewPager)
        tabs_living.setSelectedTabIndicatorGravity(TabLayout.INDICATOR_GRAVITY_BOTTOM)
        tabs_living.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!.applicationContext, R.color.lightblue))
        return root
    }

    fun newInstance(): LivingPlaceholderFragment
    {
        val args = Bundle()

        val frag = LivingPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}