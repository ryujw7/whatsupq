package com.example.whatsupq.ui.curation

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_curation_set_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class CurationPriorityAdapter(startDragListener: OnStartDragListener, val priorityList: ArrayList<String>) :
    RecyclerView.Adapter<CurationPriorityAdapter.ViewHolder>(),
    CurationPriorityTouchHelperCallback.OnItemMoveListener {

    val mStartDragListener = startDragListener

    fun getItemText(position: Int) : String {
        return priorityList[position]
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(priorityList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    interface OnStartDragListener {
        fun onStartDrag(holder: ViewHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_curation_set_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return priorityList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = priorityList[position]
        holder.apply {
            bind(item)
            itemView.curation_set_priority_btn.setOnTouchListener { view, motionEvent ->
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mStartDragListener.onStartDrag(holder)
                }
                false
            }
        }

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val view = v
        fun bind(item: String) {
            view.curation_set_priority_text.text = item
        }
    }
}
