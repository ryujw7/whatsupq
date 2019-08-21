package com.example.whatsupq.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import com.example.whatsupq.ui.PageViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ChoicePlaceholderFragment : Fragment() {

    private var itemList1 = ArrayList<ChoiceItem>()
    private var itemList2 = ArrayList<ChoiceItem>()

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
        itemList1.add(ChoiceItem("ic_launcher_foreground"))
        itemList1.add(ChoiceItem("ic_launcher_foreground"))
        itemList1.add(ChoiceItem("ic_launcher_foreground"))
        itemList1.add(ChoiceItem("ic_launcher_foreground"))
        itemList1.add(ChoiceItem("ic_launcher_foreground"))
        itemList2.add(ChoiceItem("ic_launcher_foreground"))
        itemList2.add(ChoiceItem("ic_launcher_foreground"))
        itemList2.add(ChoiceItem("ic_launcher_foreground"))
        itemList2.add(ChoiceItem("ic_launcher_foreground"))
        itemList2.add(ChoiceItem("ic_launcher_foreground"))
        val root = inflater.inflate(R.layout.fragment_home_choiceall, container, false)
        val mAdapter1 = ChoiceItemAdapter(activity!!,itemList1)
        val mAdapter2 = ChoiceItemAdapter(activity!!,itemList2)
        val firstList = root.findViewById<RecyclerView>(R.id.main_listview1)
        val secondList = root.findViewById<RecyclerView>(R.id.main_listview2)
        firstList.adapter = mAdapter1
        firstList.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        secondList.adapter = mAdapter2
        secondList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
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
        fun newInstance(sectionNumber: Int): ChoicePlaceholderFragment {
            return ChoicePlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}