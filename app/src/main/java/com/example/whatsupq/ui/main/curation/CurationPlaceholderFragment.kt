package com.example.whatsupq.ui.main.curation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_curation_init.view.*
import kotlinx.android.synthetic.main.fragment_curation_result.view.*
import kotlinx.android.synthetic.main.fragment_curation_set.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class CurationPlaceholderFragment : Fragment(), CurationPriorityAdapter.OnStartDragListener {

    lateinit var root: View

    lateinit var mItemTouchHelper: ItemTouchHelper
    val priorityList = arrayListOf("수면", "먹는 것", "인테리어", "옷 관리", "청소")
    val essentialProdList = arrayListOf(
        ProdInfo(R.mipmap.ic_launcher_round, "멋진상품", 1000),
        ProdInfo(R.mipmap.ic_launcher_round, "더멋진상품", 2000),
        ProdInfo(R.mipmap.ic_launcher_round, "완전멋진상품", 3000),
        ProdInfo(R.mipmap.ic_launcher_round, "짱멋진상품", 4000)
    )
    val optionalProdList = arrayListOf(
        ProdInfo(R.mipmap.ic_launcher_round, "멋진상품", 1000),
        ProdInfo(R.mipmap.ic_launcher_round, "더멋진상품", 2000),
        ProdInfo(R.mipmap.ic_launcher_round, "완전멋진상품", 3000),
        ProdInfo(R.mipmap.ic_launcher_round, "짱멋진상품", 4000)
    )


    override fun onStartDrag(holder: CurationPriorityAdapter.ViewHolder) {
        mItemTouchHelper.startDrag(holder)
    }

    var pageState = 0 // 0 : Init , 1 : Set , 2 : Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        when (pageState) {
            0 -> {
                root = inflater.inflate(R.layout.fragment_curation_init, container, false)
                root.curation_init_click.setOnClickListener {
                    pageState = 1
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
            1 -> {
                root = inflater.inflate(R.layout.fragment_curation_set, container, false)
                root.curation_set_range.setOnRangeSeekbarChangeListener { minValue, maxValue ->
                    root.curation_set_range_min.text = minValue.toString() + '원'
                    root.curation_set_range_max.text = maxValue.toString() + '원'
                }
                val priorityAdapter = CurationPriorityAdapter(this, priorityList)
                val mCallback = CurationPriorityTouchHelperCallback(priorityAdapter)
                mItemTouchHelper = ItemTouchHelper(mCallback)
                mItemTouchHelper.attachToRecyclerView(root.curation_set_priority_recycerview)
                root.curation_set_priority_recycerview.adapter = priorityAdapter
                root.curation_set_click.setOnClickListener {
                    pageState = 2
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
            else -> {
                root = inflater.inflate(R.layout.fragment_curation_result, container, false)
                root.curation_result_essential_recyclerview.adapter = CurationResultAdapter(essentialProdList)
                root.curation_result_optional_recyclerview.adapter = CurationResultAdapter(optionalProdList)
                root.curation_result_click.setOnClickListener {
                    pageState = 1
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
        }

        return root
    }

    fun newInstance(): CurationPlaceholderFragment {
        val args = Bundle()

        val frag = CurationPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}