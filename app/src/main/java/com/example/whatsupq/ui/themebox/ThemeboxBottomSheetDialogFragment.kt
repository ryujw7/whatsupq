package com.example.whatsupq.ui.themebox

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.whatsupq.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_themebox_bottom_sheet_dialog.view.*


class ThemeboxBottomSheetDialogFragment(cost : Int) : BottomSheetDialogFragment() {
    val itemCost = cost
    var item_amount = 1
    val MAX_COUNT = 10
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_themebox_bottom_sheet_dialog, container, false)
        root.themebox_addcart_totalcost.text = (itemCost * item_amount).toString()
        root.themebox_addcart_plus.setOnClickListener {
            item_amount++
            root.themebox_addcart_amount.text = item_amount.toString()
            root.themebox_addcart_plus.isEnabled = (item_amount < MAX_COUNT)
            root.themebox_addcart_minus.isEnabled = (item_amount > 1)
            root.themebox_addcart_totalcost.text = (itemCost * item_amount).toString()
        }
        root.themebox_addcart_minus.setOnClickListener {
            item_amount--
            root.themebox_addcart_amount.text = item_amount.toString()
            root.themebox_addcart_plus.isEnabled = (item_amount < MAX_COUNT)
            root.themebox_addcart_minus.isEnabled = (item_amount > 1)
            root.themebox_addcart_totalcost.text = (itemCost * item_amount).toString()
        }
        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

}