package com.example.whatsupq.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder

class ThemeboxListPlaceHolderFragment : Fragment() {
    var itemList = arrayListOf<ThemeboxItem>()
    lateinit var category: String
    lateinit var flag: String
    lateinit var listAdapter: ThemeboxItemAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home_themebox_listview, container, false)
        val listView = root.findViewById<ListView>(R.id.lv_themebox)
        listAdapter = ThemeboxItemAdapter(this, itemList)
        getListItem(category, flag)
        listView.adapter = listAdapter
        return root
    }
    fun selecter(themeboxItem: ThemeboxItem) : Int = themeboxItem.index
    fun sortLIst() {
        itemList.sortBy { selecter(it) }
    }
    fun newInstance(category: String, flag: String): ThemeboxListPlaceHolderFragment {
        val args = Bundle()
        val frag = ThemeboxListPlaceHolderFragment()
        frag.category = category
        frag.flag = flag
        frag.arguments = args
        return frag
    }

    private fun getListItem(category: String, flag: String) {
        lateinit var themeBoxItemJSONArray: JSONArray
        lateinit var data: JSONObject
        lateinit var imageRequest: ImageRequest
        val imgQueue = Volley.newRequestQueue(context)
        val queue = Volley.newRequestQueue(context)
        if (itemList.isNotEmpty()) {
            itemList.clear()
        }
        try {
            val jsonObjectRequest = object : JsonObjectRequest(Method.GET,
                "http://54.180.46.143:3000/api/product/themebox?category=${URLEncoder.encode(category,"utf-8")}&flag=$flag", null,
                Response.Listener {
                    var status = it.getString("status")
                    var isSuccess = it.getString("success")
                    var message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        themeBoxItemJSONArray = data.getJSONArray("themeboxes")
                        for (i in 0 until themeBoxItemJSONArray.length()) {
                            try {
                                imageRequest = ImageRequest(
                                    themeBoxItemJSONArray.getJSONObject(i).getString("main_img"),
                                    Response.Listener<Bitmap> { response ->
                                        itemList.add(
                                            ThemeboxItem(
                                                i,
                                                themeBoxItemJSONArray.getJSONObject(i).getString("themebox_id"),
                                                response,
                                                themeBoxItemJSONArray.getJSONObject(i).getString("name"),
                                                themeBoxItemJSONArray.getJSONObject(i).getString("content")
                                            )
                                        )
                                        sortLIst()
                                        listAdapter.notifyDataSetChanged()
                                    }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                                    Response.ErrorListener {
                                        Toast.makeText(context, "통신 오류", Toast.LENGTH_SHORT).show()
                                        Log.e("error", "통신 오류")
                                    }
                                )
                                imgQueue.add(imageRequest)
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        Toast.makeText(context, message + category, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", "통신 오류")
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val hashMap = HashMap<String, String>()
                    hashMap["category"] = category
                    hashMap["flag"] = flag
                    return hashMap
                }
            }
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
    }
}