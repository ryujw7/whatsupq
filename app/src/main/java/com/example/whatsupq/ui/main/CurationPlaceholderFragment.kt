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
class CurationPlaceholderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_curation, container,false)
        return root
    }

    fun newInstance(): CurationPlaceholderFragment
    {
        val args = Bundle()

        val frag = CurationPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}