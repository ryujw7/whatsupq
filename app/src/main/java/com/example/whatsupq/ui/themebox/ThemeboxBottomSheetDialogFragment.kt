package com.example.whatsupq.ui.themebox

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import com.example.whatsupq.DB.CartDBHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.whatsupq.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_themebox_bottom_sheet_dialog.view.*


class ThemeboxBottomSheetDialogFragment(var cost: Int,var product_id : String) : BottomSheetDialogFragment() {

    lateinit var cartDBHelper: CartDBHelper

    var item_amount = 1
    val MAX_COUNT = 10
    var available = true
    var currentAmountOnDB = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cartDBHelper = CartDBHelper(activity!!, null)
        val name = "테마박스 상품"
        val price = cost
        var frequency = -1
        val root = inflater.inflate(R.layout.activity_themebox_bottom_sheet_dialog, container, false)
        val cursor = cartDBHelper.getCartProduct(CartDBHelper.CART_BOX_THEME, product_id)
        if (!cursor.isAfterLast) {
            cursor.moveToFirst()
            currentAmountOnDB = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
            if (currentAmountOnDB >= MAX_COUNT) {
                root.themebox_addcart_plus.isEnabled = false
                Toast.makeText(context!!, "최대 구매할수 있는 수량은 ${MAX_COUNT}개 입니다.", Toast.LENGTH_SHORT).show()
                available = false
            }
            else if (currentAmountOnDB == MAX_COUNT - 1){
                root.themebox_addcart_plus.isEnabled = false
            }
        }
        root.themebox_addcart_totalcost.text = (cost * item_amount).toString()
        root.themebox_addcart_frequency.setOnCheckedChangeListener { radioGroup, i ->
            var frequencyString = root.findViewById<RadioButton>(i).text
            frequency = frequencyString[0].toString().toInt()
            if (available) root.themebox_addcart_final.isEnabled = true
        }
        root.themebox_addcart_plus.setOnClickListener {
            item_amount++
            root.themebox_addcart_amount.text = item_amount.toString()
            root.themebox_addcart_plus.isEnabled = (item_amount + currentAmountOnDB < MAX_COUNT)
            root.themebox_addcart_minus.isEnabled = (item_amount > 1)
            root.themebox_addcart_totalcost.text = (cost * item_amount).toString()
        }
        root.themebox_addcart_minus.setOnClickListener {
            item_amount--
            root.themebox_addcart_amount.text = item_amount.toString()
            root.themebox_addcart_plus.isEnabled = (item_amount + currentAmountOnDB < MAX_COUNT)
            root.themebox_addcart_minus.isEnabled = (item_amount > 1)
            root.themebox_addcart_totalcost.text = (cost * item_amount).toString()
        }
        root.themebox_addcart_final.setOnClickListener {
            cartDBHelper = CartDBHelper(context!!, null)

            val cursor = cartDBHelper.getCartProduct("CART_BOX_THEME", product_id)
            if (cursor!!.isAfterLast) {
                cartDBHelper.addToCart("CART_BOX_THEME", product_id, name, price, item_amount, frequency)
                Toast.makeText(context!!, "상품을 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
            } else {
                cursor.moveToFirst()
                val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                if (amount < MAX_COUNT) {
                    cartDBHelper.updateAmount("CART_BOX_THEME", product_id, amount + item_amount)
                    cartDBHelper.updateFrequency("CART_BOX_THEME", product_id, frequency)
                    Toast.makeText(context!!, "상품을 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
                }
            }
            dismiss()
            activity!!.finish()
        }
        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).state =
                    BottomSheetBehavior.STATE_EXPANDED
        }
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        return dialog
    }
}