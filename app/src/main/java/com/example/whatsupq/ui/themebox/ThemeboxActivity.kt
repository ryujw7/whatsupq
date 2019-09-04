package com.example.whatsupq.ui.themebox

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_themebox.*
import kotlinx.android.synthetic.main.fragment_living_item_info.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themebox)
        imgQueue = Volley.newRequestQueue(this)
        themeboxid = intent.getStringExtra("themebox_id")
        queue = Volley.newRequestQueue(this)

        val adapter = ThemeViewpagerAdapter(supportFragmentManager, imgSrcList)
        themebox_viewpager.adapter = adapter
        themebox_indicator.setupWithViewPager(themebox_viewpager, true)

        try {
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "http://54.180.46.143:3000/api/product/themebox/detail?themebox_id=$themeboxid",null,
                Response.Listener {
                    status = it.getString("status")
                    isSuccess = it.getString("success")
                    message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        Log.e("data", data.toString())
//                        main_banner.text = data.getString("name")
                        price = data.getInt("price")
                        themebox_cost.text = price.toString()
                        imgArray = data.getJSONArray("img")
                        val bottomSheetDialogFragment = ThemeboxBottomSheetDialogFragment(price)
                        themebox_addcart.setOnClickListener {
                            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
                        }
                        for (imgIndex in 0 until imgArray.length()) {
                            try {
                                imageRequest = ImageRequest(
                                    imgArray.getString(imgIndex),
                                    Response.Listener<Bitmap> { response ->
                                        imgSrcList.add(LivingInfoImage(imgIndex, response))
                                        sortList()
                                        adapter.notifyDataSetChanged()
                                    }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565,
                                    Response.ErrorListener {
                                        Toast.makeText(this, "통신 오류", Toast.LENGTH_SHORT).show()
                                        Log.e("error", "통신 오류")
                                    }
                                )
                                imgQueue.add(imageRequest)
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        imgQueue.cache.clear()
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", "통신 오류")
                })
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
    }

    fun selector(livingInfoImage: LivingInfoImage): Int = livingInfoImage.index
    fun sortList() {
        imgSrcList.sortBy { selector(it) }
    }
}
