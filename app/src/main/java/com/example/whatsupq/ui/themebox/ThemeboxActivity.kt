package com.example.whatsupq.ui.themebox

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.DB.SharedPreferenceController
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_themebox.*
import org.json.JSONException
import org.json.JSONObject

class ThemeboxActivity : BaseActivity() {
    val cost = 21900
    var imgSrcList = arrayListOf(
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round,
        R.mipmap.ic_launcher_round
    )
    lateinit var themeboxid : String
    lateinit var queue : RequestQueue
    lateinit var jsonObject : JSONObject
    lateinit var status : String
    lateinit var message : String
    lateinit var isSuccess : String
    lateinit var data : JSONObject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themebox)
        themeboxid = intent.getStringExtra("themebox_id")
        try {
            queue = Volley.newRequestQueue(applicationContext)
            jsonObject = JSONObject()
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,
                "http://54.180.46.143:3000/api/product/themebox/detail?themebox_id=$themeboxid", null,
                Response.Listener {
                    status = it.getString("status")
                    isSuccess = it.getString("success")
                    message = it.getString("message")
                    if (status.equals("200")) {
                        val intent = Intent()
                        data = it.getJSONObject("data")
                        val token = data.getString("token")
                        SharedPreferenceController.setUserToken(applicationContext, token)
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        setResult(status.toInt(), intent)
                        finish()
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", "통신 오류")
                })
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
        themebox_cost.text = cost.toString()
        themebox_viewpager.adapter = ThemeViewpagerAdapter(supportFragmentManager, imgSrcList)
        themebox_indicator.setupWithViewPager(themebox_viewpager, true)
        val bottomSheetDialogFragment = ThemeboxBottomSheetDialogFragment(cost)
        themebox_addcart.setOnClickListener {
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
}
