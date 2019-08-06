package com.example.whatsupq.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import com.example.whatsupq.ui.PageViewModel
import com.google.android.material.tabs.TabLayout

class LivingPlaceholderFragment : Fragment() {
    private lateinit var pageViewModel: PageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

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
        tabs_living.setSelectedTabIndicatorColor(Color.rgb(16,106,150))
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
        fun newInstance(sectionNumber: Int): LivingPlaceholderFragment {
            return LivingPlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}