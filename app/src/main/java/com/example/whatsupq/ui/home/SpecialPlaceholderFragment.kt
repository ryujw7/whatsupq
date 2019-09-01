package com.example.whatsupq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.whatsupq.R

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

    fun newInstance(): SpecialPlaceholderFragment
    {
        val args = Bundle()

        val frag = SpecialPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}