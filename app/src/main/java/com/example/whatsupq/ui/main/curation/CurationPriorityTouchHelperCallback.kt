package com.example.whatsupq.ui.main.curation

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class CurationPriorityTouchHelperCallback(listener: OnItemMoveListener) : ItemTouchHelper.Callback() {

    val mItemMoveListener = listener

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mItemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    interface OnItemMoveListener {
        fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

}