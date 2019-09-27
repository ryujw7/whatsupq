package com.example.whatsupq.ui.buy

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_buy.*
import android.view.animation.Animation
import android.view.animation.Transformation
import com.example.whatsupq.ResultTradeActivity
import java.util.*


class BuyActivity : BaseActivity() {
    var prodList = arrayListOf(
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher
    )
    var cardList = arrayListOf(
        CardInfo("KEB 하나", 1234, 5678, 9012, 3456),
        CardInfo("KEB 둘", 4567, 5678, 9012, 3456),
        CardInfo("KEB 셋", 8495, 5678, 9012, 3456)
    )
    var user_name = "이이름"
    var user_phone = "010-1234-5678"
    var user_email = "example@gmail.com"
    var destination_post = ""
    var destination_address = ""
    var destination_specific = ""
    var request_msg = ""

    val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        buy_customerinfo_user_name.text = user_name
        buy_customerinfo_simple_user_name.text = user_name
        buy_customerinfo_simple_user_phone.text = user_phone
        buy_customerinfo_user_phone.text = user_phone
        buy_customerinfo_user_email.text = user_email

        buy_expand_prodinfo.setOnClickListener {
            when (buy_expand_prodinfo_target.visibility) {
                View.VISIBLE -> buy_expand_prodinfo_btn.setImageResource(R.drawable.expand)
                else -> buy_expand_prodinfo_btn.setImageResource(R.drawable.collapse)
            }
            toggleExpand(buy_expand_prodinfo_target)
        }
        buy_prodinfo_recyclerview.adapter = BuyProdAdapter(this, prodList)

        buy_expand_customerinfo.setOnClickListener {
            when (buy_expand_customerinfo_target.visibility) {
                View.VISIBLE -> {
                    buy_expand_customerinfo_target_btn.setImageResource(R.drawable.expand)
                    buy_customerinfo_simple.visibility = View.VISIBLE
                }
                else -> {
                    buy_expand_customerinfo_target_btn.setImageResource(R.drawable.collapse)
                    buy_customerinfo_simple.visibility = View.GONE
                }
            }
            toggleExpand(buy_expand_customerinfo_target)
        }
        buy_expand_destination.setOnClickListener {
            if (destination_address.isNotEmpty()) {
                when (buy_expand_destination_target.visibility) {
                    View.VISIBLE -> {
                        buy_expand_destination_target_btn.setImageResource(R.drawable.expand)
                    }
                    else -> {
                        buy_expand_destination_target_btn.setImageResource(R.drawable.collapse)
                    }
                }
                toggleExpand(buy_expand_destination_target)
            }
        }
        buy_destination_register.setOnClickListener {
            intent = Intent(this, BuyDestFindActivity::class.java)
            startActivityForResult(intent,2000)
        }
        val bottomsheet = BuyReqBottomSheepDialogFragment()
        buy_expand_request.setOnClickListener {
            if (request_msg.isNotEmpty()) {
                when (buy_expand_request_target.visibility) {
                    View.VISIBLE -> {
                        buy_expand_request_target_btn.setImageResource(R.drawable.expand)
                        buy_request_simple.visibility = View.VISIBLE
                    }
                    else -> {
                        buy_expand_request_target_btn.setImageResource(R.drawable.collapse)
                        buy_request_simple.visibility = View.GONE
                    }
                }
                toggleExpand(buy_expand_request_target)
            }
        }
        buy_request_register.setOnClickListener {
            bottomsheet.show(supportFragmentManager, bottomsheet.tag)
        }
        buy_request_btn.setOnClickListener {
            bottomsheet.show(supportFragmentManager, bottomsheet.tag)
        }
        buy_expand_frequency.setOnClickListener {
            when (buy_expand_frequency_target.visibility) {
                View.VISIBLE -> buy_expand_frequency_target_btn.setImageResource(R.drawable.expand)
                else -> buy_expand_frequency_target_btn.setImageResource(R.drawable.collapse)
            }
            toggleExpand(buy_expand_frequency_target)
        }
        buy_frequency.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()
            }
            val calendar = DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            calendar.datePicker.minDate = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2
            calendar.show()
        }
        buy_expand_payment.setOnClickListener {
            when (buy_expand_payment_target.visibility) {
                View.VISIBLE -> buy_expand_payment_target_btn.setImageResource(R.drawable.expand)
                else -> buy_expand_payment_target_btn.setImageResource(R.drawable.collapse)
            }
            toggleExpand(buy_expand_payment_target)
        }
        buy_payment_recyclerview.adapter = BuyCardAdapter(this, cardList)
        buy_expand_total.setOnClickListener {
            when (buy_expand_total_target.visibility) {
                View.VISIBLE -> buy_expand_total_target_btn.setImageResource(R.drawable.expand)
                else -> buy_expand_total_target_btn.setImageResource(R.drawable.collapse)
            }
            toggleExpand(buy_expand_total_target)
        }
        themebox_addcart_final.setOnClickListener {
            startActivity(Intent(this, ResultTradeActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun updateDate() {
        buy_frequency_date.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun toggleExpand(view: View) {
        val savedheight = view.height
        when (view.visibility) {
            View.VISIBLE -> {
                collapse(view)
            }

            View.GONE -> {
                expand(view)
            }
            else -> view.visibility = View.VISIBLE
        }
    }

    fun expand(v: View) {
        val matchParentMeasureSpec =
            View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
        val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight = v.measuredHeight

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    ViewGroup.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // Expansion speed of 1dp/ms
        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight = v.measuredHeight

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // Collapse speed of 1dp/ms
        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                destination_post = data!!.getStringExtra("zoneCode")
                destination_address = data!!.getStringExtra("address")
                destination_specific = data!!.getStringExtra("buildingName")
                buy_destination_simple.text = destination_address
                buy_destination_post.text = destination_post
                buy_destination_address.text = destination_address
                buy_destination_specific.text = destination_specific
            }
        }
    }
}
