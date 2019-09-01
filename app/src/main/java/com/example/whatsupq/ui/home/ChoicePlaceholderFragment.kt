package com.example.whatsupq.ui.home

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import kotlinx.android.synthetic.main.fragment_home_themebox_item.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * A placeholder fragment containing a simple view.
 */
class ChoicePlaceholderFragment : Fragment() {

    private var itemList1 = ArrayList<ChoiceItem>()
    private var itemList2 = ArrayList<ChoiceItem>()

    lateinit var mAdapter2 : ChoiceItemAdapter
    lateinit var mAdapter1 : ChoiceItemAdapter
    lateinit var mAdapter3 : HomeBannerItemAdapter
    lateinit var themebox : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_choiceall, container, false)
        themebox = root.findViewById<View>(R.id.fragment_home_theme_new1)
        if(!itemList1.isEmpty()) {
            itemList1.clear()
        }
        if(!itemList2.isEmpty()) {
            itemList2.clear()
        }
        mAdapter1 = ChoiceItemAdapter(activity!! as Context, itemList1)
        mAdapter2 = ChoiceItemAdapter(activity!! as Context, itemList2)
        mAdapter3 = HomeBannerItemAdapter(activity!!.supportFragmentManager)
        getBitmap()
        var firstList = root.findViewById<RecyclerView>(R.id.main_listview1)
        var secondList = root.findViewById<RecyclerView>(R.id.main_listview2)
        val bannerList: SwipeViewPager = root.findViewById(R.id.fragment_home_banner)
        firstList.adapter = mAdapter1
        secondList.adapter = mAdapter2
        firstList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        secondList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        bannerList.setPagingEnabled(true)
        bannerList.adapter = mAdapter3
        return root
    }

    fun newInstance(): ChoicePlaceholderFragment {
        val args = Bundle()

        val frag = ChoicePlaceholderFragment()
        frag.arguments = args

        return frag
    }
    fun getBitmap() {
        lateinit var specialJSONArray: JSONArray
        lateinit var todayJsonArray: JSONArray
        lateinit var productJsonArray: JSONArray
        lateinit var themeBoxJsonObject: JSONObject
        lateinit var data: JSONObject
        lateinit var imageRequest: ImageRequest
        val imgQueue = Volley.newRequestQueue(context)
        val queue = Volley.newRequestQueue(context)
        try {
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "http://54.180.46.143:3000/api/product/main", null,
                Response.Listener {
                    var status = it.getString("status")
                    var isSuccess = it.getString("success")
                    var message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        specialJSONArray = data.getJSONArray("special")
                        todayJsonArray = data.getJSONArray("today")
                        productJsonArray = data.getJSONArray("product")
                        themeBoxJsonObject = data.getJSONObject("themabox")
                        for (i in 0 until specialJSONArray.length()) {
                            try {
                                imageRequest = ImageRequest(
                                    specialJSONArray.getJSONObject(i).getString("main_img"),
                                    object : Response.Listener<Bitmap> {
                                        override fun onResponse(response: Bitmap) {
                                            mAdapter3.resIdList.add(response)
                                            mAdapter3.notifyDataSetChanged()
                                        }
                                    }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                                    object : Response.ErrorListener {
                                        override fun onErrorResponse(error: VolleyError?) {
                                            Toast.makeText(context, "통신 오류", Toast.LENGTH_SHORT).show()
                                            Log.e("error", "통신 오류")
                                        }
                                    }
                                )
                                imgQueue.add(imageRequest)
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        for (i in 0 until todayJsonArray.length()) {
                            try {
                                imageRequest = ImageRequest(
                                    todayJsonArray.getJSONObject(i).getString("main_img"),
                                    object : Response.Listener<Bitmap> {
                                        override fun onResponse(response: Bitmap) {
                                            itemList1.add(ChoiceItem(response))
                                            mAdapter1.notifyDataSetChanged()

                                        }
                                    }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                                    object : Response.ErrorListener {
                                        override fun onErrorResponse(error: VolleyError?) {
                                            Toast.makeText(context, "통신 오류", Toast.LENGTH_SHORT).show()
                                            Log.e("error", "통신 오류")
                                        }
                                    }
                                )
                                imgQueue.add(imageRequest)
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        for (i in 0 until productJsonArray.length()) {
                            try {
                                imageRequest = ImageRequest(
                                    productJsonArray.getJSONObject(i).getString("main_img"),
                                    object : Response.Listener<Bitmap> {
                                        override fun onResponse(response: Bitmap) {
                                            itemList2.add(ChoiceItem(response))
                                            mAdapter2.notifyDataSetChanged()
                                        }
                                    }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                                    object : Response.ErrorListener {
                                        override fun onErrorResponse(error: VolleyError?) {
                                            Toast.makeText(context, "통신 오류", Toast.LENGTH_SHORT).show()
                                            Log.e("error", "통신 오류")
                                        }

                                    }
                                )
                                imgQueue.add(imageRequest)
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        try {
                            imageRequest = ImageRequest(
                                themeBoxJsonObject.getString("main_img"),
                                object : Response.Listener<Bitmap> {
                                    override fun onResponse(response: Bitmap) {
                                        val float = 12
                                        val result = setRoundCorner(response,float.toFloat())
                                        themebox.theme_image.scaleType = ImageView.ScaleType.CENTER_CROP
                                        themebox.theme_image.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY)
                                        themebox.theme_image.setImageBitmap(response)
                                        themebox.theme_image.clipToOutline = true
                                    }
                                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                                object : Response.ErrorListener {
                                    override fun onErrorResponse(error: VolleyError?) {
                                        Toast.makeText(context, "통신 오류", Toast.LENGTH_SHORT).show()
                                        Log.e("error", "통신 오류")
                                    }
                                }
                            )
                            imgQueue.add(imageRequest)
                        } catch (e: JSONException) {
                            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", "통신 오류")
                })
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
    }
    fun setRoundCorner(bitmap: Bitmap, pixel : Float) : Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = 0xff424242
        val paint = Paint()
        val rect = Rect(0,0,bitmap.width, bitmap.height)
        val rectf = RectF(rect)

        paint.isAntiAlias = true
        paint.color = color.toInt()
        canvas.drawARGB(0,0,0,0)
        canvas.drawRoundRect(rectf, pixel, pixel, paint)

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)


        return output
    }
}

