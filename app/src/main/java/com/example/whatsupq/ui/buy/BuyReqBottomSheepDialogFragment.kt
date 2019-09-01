package com.example.whatsupq.ui.buy

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import com.example.whatsupq.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.activity_buy_request_bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.activity_buy_request_bottom_sheet_dialog.view.*

class BuyReqBottomSheepDialogFragment() : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_buy_request_bottom_sheet_dialog, container, false)
        view.buy_request_group.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.buy_request_self_btn) {
                view.buy_request_self_txt.isEnabled = true
                view.buy_request_self_txt.setBackgroundResource(R.drawable.edittext_request_enabled)
                view.buy_request_self_txt.requestFocus()
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(buy_request_self_txt, InputMethodManager.SHOW_IMPLICIT)
                buy_request_self_txt.setOnEditorActionListener { v, actionID, keyEvent ->
                    if (actionID == EditorInfo.IME_ACTION_DONE) {
                        (activity as BuyActivity).buy_request_txt.text = v.text
                        if((activity as BuyActivity).request_msg.isEmpty()){
                            (activity as BuyActivity).request_msg = v.text.toString()
                            (activity as BuyActivity).buy_expand_request.performClick()
                        }
                        (activity as BuyActivity).request_msg = v.text.toString()
                        (activity as BuyActivity).buy_request_register.visibility = View.GONE
                        (activity as BuyActivity).buy_request_simple.text = v.text
                        dismiss()
                        true
                    } else {
                        false
                    }
                }
            } else {
                view.buy_request_self_txt.isEnabled = false
                view.buy_request_self_txt.setBackgroundResource(R.drawable.button_frequency)
                val btn: RadioButton = view.findViewById(buy_request_group.checkedRadioButtonId)
                (activity as BuyActivity).buy_request_txt.text = btn.text
                if((activity as BuyActivity).request_msg.isEmpty()){
                    (activity as BuyActivity).request_msg = btn.text.toString()
                    (activity as BuyActivity).buy_expand_request.performClick()
                }
                (activity as BuyActivity).request_msg = btn.text.toString()
                (activity as BuyActivity).buy_request_register.visibility = View.GONE
                (activity as BuyActivity).buy_request_simple.text = btn.text
                dismiss()
            }
        }

        return view
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