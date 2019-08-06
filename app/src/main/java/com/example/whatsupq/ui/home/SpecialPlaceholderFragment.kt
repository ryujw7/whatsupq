package com.example.whatsupq.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import com.example.whatsupq.ui.PageViewModel
import com.google.android.material.tabs.TabLayout

class SpecialPlaceholderFragment : Fragment() {
    var itemList = arrayListOf<SpecialItem>(
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground"),
        SpecialItem("ic_launcher_foreground")
    )
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
        val root = inflater.inflate(R.layout.fragment_home_special, container, false)
        val listView = root.findViewById<ListView>(R.id.lv_special)
        val specialListAdapter = SpecialItemAdapter(this, itemList)
        listView.adapter = specialListAdapter
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
        fun newInstance(sectionNumber: Int): SpecialPlaceholderFragment {
            return SpecialPlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}