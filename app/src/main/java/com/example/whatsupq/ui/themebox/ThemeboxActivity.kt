package com.example.whatsupq.ui.themebox

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.DB.SharedPreferenceController
import com.example.whatsupq.LivingInfoImage
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.activity_themebox.*
import kotlinx.android.synthetic.main.fragment_living_item_info.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class ThemeboxActivity : BaseActivity() {
    var price = 0
    var imgSrcList = arrayListOf<LivingInfoImage>()
    lateinit var themeboxid: String
    lateinit var queue: RequestQueue
    lateinit var status: String
    lateinit var message: String
    lateinit var isSuccess: String
    lateinit var data: JSONObject
    lateinit var imgArray: JSONArray
    lateinit var imageRequest: ImageRequest
    lateinit var imgQueue: RequestQueue
    lateinit var dialog: Dialog
    lateinit var anim: Animation
    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themebox)
        imgQueue = Volley.newRequestQueue(this)
        themeboxid = intent.getStringExtra("themebox_id")
        queue = Volley.newRequestQueue(this)
        anim = AnimationUtils.loadAnimation(this, R.anim.loading)
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.loading_img.animation = anim
        val adapter = ThemeViewpagerAdapter(supportFragmentManager, imgSrcList)
        themebox_indicator.setupWithViewPager(themebox_viewpager, true)
        back_btn.setOnClickListener {
            finish()
        }
        try {
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "http://54.180.46.143:3000/api/product/themebox/detail?themebox_id=$themeboxid", null,
                Response.Listener {
                    status = it.getString("status")
                    isSuccess = it.getString("success")
                    message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        Log.e("data", data.toString())
                        price = data.getInt("price")
                        themebox_cost.text = format.format(price)
                        imgArray = data.getJSONArray("img")
                        val bottomSheetDialogFragment = ThemeboxBottomSheetDialogFragment(price, themeboxid)
                        themebox_addcart.setOnClickListener {
                            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
                        }
                        for (i in 0 until imgArray.length() - 1) {
                            imgSrcList.add(LivingInfoImage(i, imgArray[i].toString()))
                            adapter.notifyDataSetChanged()
                        }
                        imgQueue.cache.clear()
                        Log.d("message : ", message)
                    } else {
                        Log.d("message : ", message)
                    }
                },
                Response.ErrorListener {
                    Log.e("error", "통신 오류")
                })
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
        Thread(Runnable {
            run {
                handler.post {
                    run {
                        dialog.show()
                    }
                }
                try {
                    Thread.sleep(1500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post {
                    run {
                        themebox_viewpager.adapter = adapter
                        dialog.dismiss()
                    }
                }
            }
        }).start()
    }

    fun selector(livingInfoImage: LivingInfoImage): Int = livingInfoImage.index
    fun sortList() {
        imgSrcList.sortBy { selector(it) }
    }
}
