package com.example.whatsupq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.example.whatsupq.R

class ThemeboxListPlaceHolderFragment : Fragment() {
    var itemList = arrayListOf<ThemeboxItem>(
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀"),
        ThemeboxItem("ic_launcher_foreground", "타이틀", "서브타이틀")
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home_themebox_listview, container, false)
        val listView = root.findViewById<ListView>(R.id.lv_themebox)
        val listAdapter = ThemeboxItemAdapter(this, itemList)
        listView.adapter = listAdapter
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
        fun newInstance(sectionNumber: Int): ThemeboxListPlaceHolderFragment {
            return ThemeboxListPlaceHolderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}