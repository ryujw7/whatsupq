package com.example.whatsupq

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import com.example.whatsupq.DB.CartDBHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_living_bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.activity_living_item_info.view.*
import java.text.DecimalFormat

class LivingItemBottomSheetDialogFragment(var cost: String, var itemName : String, var product_id: String) : BottomSheetDialogFragment() {

    lateinit var cartDBHelper: CartDBHelper

    var item_amount = 1
    val MAX_COUNT = 10
    var available = true
    var currentAmountOnDB = 0

    val format = DecimalFormat("###,###")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cartDBHelper = CartDBHelper(activity!!, null)
        val name = itemName
        val price = cost
        var frequency = -1
        val root = inflater.inflate(R.layout.activity_living_bottom_sheet_dialog, container, false)
        val cursor = cartDBHelper.getCartProduct(CartDBHelper.CART_ESSENTIAL, product_id)
        if (!cursor.isAfterLast) {
            cursor.moveToFirst()
            currentAmountOnDB = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
            if (currentAmountOnDB >= MAX_COUNT) {
                root.living_addcart_plus.isEnabled = false
                Toast.makeText(context!!, "최대 구매할수 있는 수량은 ${MAX_COUNT}개 입니다.", Toast.LENGTH_SHORT).show()
                available = false
            } else if (currentAmountOnDB == MAX_COUNT - 1) {
                root.living_addcart_plus.isEnabled = false
            }
        }
        root.living_addcart_minus.isEnabled = item_amount > 1
        root.living_addcart_totalcost.text = format.format(cost.toInt() * item_amount)
        root.living_addcart_amount.text = item_amount.toString()
        root.living_addcart_frequency.setOnCheckedChangeListener { radioGroup, i ->
            var frequencyString = root.findViewById<RadioButton>(i).text
            frequency = frequencyString[0].toString().toInt()
            if (available) root.living_addcart_final.isEnabled = true
        }
        root.living_addcart_plus.setOnClickListener {
            item_amount++
            root.living_addcart_amount.text = item_amount.toString()
            root.living_addcart_plus.isEnabled = (item_amount + currentAmountOnDB < MAX_COUNT)
            root.living_addcart_minus.isEnabled = (item_amount > 1)
            root.living_addcart_totalcost.text = format.format(cost.toInt() * item_amount)
        }
        root.living_addcart_minus.setOnClickListener {
            item_amount--
            root.living_addcart_amount.text = item_amount.toString()
            root.living_addcart_plus.isEnabled = (item_amount + currentAmountOnDB < MAX_COUNT)
            root.living_addcart_minus.isEnabled = (item_amount > 1)
            root.living_addcart_totalcost.text = format.format(cost.toInt() * item_amount)
        }
        root.living_addcart_final.setOnClickListener {
            cartDBHelper = CartDBHelper(root.context, null)
            val cursor = cartDBHelper.getCartProduct("CART_ESSENTIAL", product_id)
            if (cursor!!.isAfterLast) { // 중복된 상품이 없다면
                cartDBHelper.addToCart(
                    "CART_ESSENTIAL",
                    product_id,
                    name,
                    price.toInt(),
                    item_amount,
                    frequency
                )
                Toast.makeText(context!!, "상품을 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
            }
            else {
                cursor.moveToFirst()
                val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                if (amount < MAX_COUNT) {
                    cartDBHelper.updateAmount("CART_ESSENTIAL", product_id, amount + 1)
                    cartDBHelper.updateFrequency("CART_ESSENTIAL", product_id, frequency)
                    Toast.makeText(context!!, "상품을 장바구니에 담았습니다", Toast.LENGTH_SHORT).show()
                }
            }
            // 장바구니 DB에 상품 추가 끝
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