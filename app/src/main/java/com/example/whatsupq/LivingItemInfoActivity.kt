package com.example.whatsupq

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.DB.CartDBHelper
import com.example.whatsupq.ui.home.LivingItem
import kotlinx.android.synthetic.main.activity_living_item_info.*
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.fragment_living_item_info.*
import kotlinx.android.synthetic.main.fragment_living_item_info.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.InterruptedException
import java.net.URLEncoder

class LivingItemInfoActivity : BaseActivity() {


    lateinit var cartDBHelper: CartDBHelper
    lateinit var product_id: String

    var itemList = arrayListOf<LivingInfoImage>()
    val handler = Handler()
    lateinit var anim: Animation
    lateinit var dialog: Dialog
    lateinit var mAdapter: LivingItemInfoImageAdapter
    lateinit var data: JSONObject
    lateinit var imgArray: JSONArray
    lateinit var imageRequest: ImageRequest
    lateinit var imgQueue: RequestQueue
    lateinit var queue : RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_item_info)
        imgQueue = Volley.newRequestQueue(this)
        queue = Volley.newRequestQueue(this)
        product_id = intent.getStringExtra("product_id")
        anim = AnimationUtils.loadAnimation(this, R.anim.loading)
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.loading_img.animation = anim
        val view = View.inflate(this, R.layout.fragment_living_item_info, null)
        mAdapter = LivingItemInfoImageAdapter(this, itemList)
        try {
            val jsonObjectRequest = object : JsonObjectRequest(Method.GET,
                "http://54.180.46.143:3000/api/product/regular/detail?product_id=$product_id", null,
                Response.Listener {
                    var status = it.getString("status")
                    var isSuccess = it.getString("success")
                    var message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        Log.e("data ", data.toString())
                        view.brand_name.text = data.getString("name")
                        view.item_name.text = data.getString("content")
                        view.charge.text = data.getString("saled_price") + "원"
                        view.beforecharge.text = data.getString("price") + "원"
                        try {
                            imageRequest = ImageRequest(
                                data.getString("main_img"),
                                Response.Listener<Bitmap> { response ->
                                    view.living_info_main_image.setImageBitmap(response)
                                }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                                Response.ErrorListener {
                                    Toast.makeText(this, "통신 오류", Toast.LENGTH_SHORT).show()
                                    Log.e("error", "통신 오류")
                                }
                            )
                            imgQueue.add(imageRequest)
                        } catch (e: JSONException) {
                            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                        }
                        imgArray = data.getJSONArray("content_img")
                        for (i in 0 until imgArray.length()) {
                            try {
                                imageRequest = ImageRequest(
                                    imgArray.getString(i),
                                    Response.Listener<Bitmap> { response ->
                                        itemList.add(LivingInfoImage(i, response))
                                        sortLIst()
                                        mAdapter.notifyDataSetChanged()
                                    }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
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
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", "통신 오류")
                }) {

            }
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
        Thread(Runnable {
            run {
                handler.post(Runnable {
                    run {
                        dialog.show()
                    }
                })
                try {
                    Thread.sleep(1200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post(Runnable {
                    run {
                        view.living_info_image_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        view.living_info_image_list.adapter = mAdapter
                        fragment_living_item_info.addView(view)
                        dialog.dismiss()
                    }
                })
            }
        }).start()

        living_item_info_addcart.setOnClickListener {
            // 장바구니 DB에 상품 추가
            cartDBHelper = CartDBHelper(this, null)
            val cursor = cartDBHelper.getCartProduct("CART_ESSENTIAL", product_id)
            if (cursor!!.isAfterLast) // 중복된 상품이 없다면
                cartDBHelper.addToCart("CART_ESSENTIAL", product_id,view.item_name.text.toString() , view.charge.text.toString().toInt(), 1, 1)
            else {
                cursor.moveToFirst()
                val amount = cursor.getInt(cursor.getColumnIndex("AMOUNT"))
                if (amount < 10) {
                    cartDBHelper.updateAmount("CART_ESSENTIAL", product_id, amount + 1)
                }
            }
            // 장바구니 DB에 상품 추가 끝
            finish()
        }
    }

    fun selecter(livingInfoImage: LivingInfoImage): Int = livingInfoImage.index
    fun sortLIst() {
        itemList.sortBy { selecter(it) }
    }
}