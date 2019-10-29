package com.example.whatsupq.ui.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.whatsupq.R
import com.example.whatsupq.SwipeViewPager
import com.example.whatsupq.ui.themebox.ThemeboxActivity
import kotlinx.android.synthetic.main.activity_living_item_info.*
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.fragment_home_themebox_item.view.*
import kotlinx.android.synthetic.main.fragment_living_item_info.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.collections.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
class ChoicePlaceholderFragment : Fragment() {

    private var itemList1 = ArrayList<ChoiceItem>()
    private var itemList2 = ArrayList<ChoiceItem>()
    private var specialList = ArrayList<String>()
    lateinit var mAdapter2: ChoiceItemAdapter
    lateinit var mAdapter1: ChoiceItemAdapter
    lateinit var mAdapter3: HomeBannerItemAdapter
    lateinit var themebox: View
    lateinit var dialog: Dialog
    lateinit var anim: Animation
    lateinit var firstList: RecyclerView
    lateinit var secondList: RecyclerView
    lateinit var bannerList: SwipeViewPager
    private val handler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_choiceall, container, false)
        anim = AnimationUtils.loadAnimation(root.context, R.anim.loading)
        dialog = Dialog(root.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.loading_img.animation = anim
        themebox = root.findViewById(R.id.fragment_home_theme_new1)
        firstList = root.findViewById(R.id.main_listview1)
        secondList = root.findViewById(R.id.main_listview2)
        bannerList = root.findViewById(R.id.fragment_home_banner)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!itemList1.isEmpty()) {
            itemList1.clear()
        }
        if (!itemList2.isEmpty()) {
            itemList2.clear()
        }
        mAdapter1 = ChoiceItemAdapter(activity!! as Context, itemList1)
        mAdapter2 = ChoiceItemAdapter(activity!! as Context, itemList2)
        mAdapter3 = HomeBannerItemAdapter(activity!!.supportFragmentManager)
        getBitmap()
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
                        firstList.adapter = mAdapter1
                        secondList.adapter = mAdapter2
                        firstList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        secondList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        dialog.dismiss()
                    }
                })
            }
        }).start()
        bannerList.setPagingEnabled(true)
        bannerList.adapter = mAdapter3
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
        lateinit var themebox_id: String
        val imgQueue = Volley.newRequestQueue(context)
        val queue = Volley.newRequestQueue(context)
        try {
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "http://54.180.46.143:3000/api/product/main", null,
                Listener {
                    var status = it.getString("status")
                    var isSuccess = it.getString("success")
                    var message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        Log.e("dataset : ", data.toString())
                        specialJSONArray = data.getJSONArray("special")
                        todayJsonArray = data.getJSONArray("today")
                        productJsonArray = data.getJSONArray("product")
                        themeBoxJsonObject = data.getJSONObject("themebox")
                        for (i in 0 until specialJSONArray.length()) {
                            specialList.add(specialJSONArray.getJSONObject(i).getString("main_img"))
                        }
                        for (i in 0 until specialJSONArray.length()) {
                            try {
                                if (mAdapter3.resIdList.isNotEmpty()) {
                                    mAdapter3.resIdList.clear()
                                }
                                imageRequest = ImageRequest(
                                    specialList[i],
                                    Listener<Bitmap> { response ->
                                        mAdapter3.resIdList.add(BannerList(i, response))
                                        mAdapter3.sortList()
                                        mAdapter3.notifyDataSetChanged()
                                    }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                                    Response.ErrorListener {
                                        Log.e("error", "통신 오류")
                                    }
                                )
                                imageRequest.setShouldCache(false)
                                imgQueue.add(imageRequest)
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        for (j in 0 until 10) {
                            var id = todayJsonArray.getJSONObject(j).getString("product_id")
                            try {
                                itemList1.add(ChoiceItem(j, id, todayJsonArray.getJSONObject(j).getString("main_img")))
                                sortLIst(itemList1)
                                mAdapter1.notifyDataSetChanged()
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        for (k in 0 until productJsonArray.length()) {
                            var id = productJsonArray.getJSONObject(k).getString("product_id")
                            try {
                                itemList2.add(
                                    ChoiceItem(
                                        k,
                                        id,
                                        productJsonArray.getJSONObject(k).getString("main_img")
                                    )
                                )
                                sortLIst(itemList2)
                                mAdapter2.notifyDataSetChanged()
                            } catch (e: JSONException) {
                                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                            }
                        }
                        try {
                            themebox_id = themeBoxJsonObject.getString("themebox_id")
                            val float = 12
                            themebox.theme_image.scaleType = ImageView.ScaleType.CENTER_CROP
                            themebox.theme_image.setColorFilter(
                                Color.parseColor("#BDBDBD"),
                                PorterDuff.Mode.MULTIPLY
                            )
                            Glide.with(this).load(themeBoxJsonObject.getString("main_img")).into(themebox.theme_image)
                            themebox.theme_title.text = themeBoxJsonObject.getString("name")
                            themebox.theme_subtitle.text = themeBoxJsonObject.getString("content")
                            themebox.theme_image.clipToOutline = true
                            themebox.theme_image.setOnClickListener {
                                val intent = Intent(context, ThemeboxActivity::class.java)
                                intent.putExtra("themebox_id", themebox_id)
                                startActivity(intent)
                            }
                            imageRequest.setShouldCache(false)
                            imgQueue.add(imageRequest)
                        } catch (e: JSONException) {
                            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                        }
                    } else {
                        Log.d("message", message)
                    }
                }, Response.ErrorListener {
                    Log.e("error", "통신 오류")
                })
            jsonObjectRequest.setShouldCache(false)
            queue.add(jsonObjectRequest)
        } catch (e: JSONException) {
            Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
        }
    }

    private fun selecter(choiceItem: ChoiceItem): Int = choiceItem.index
    private fun sortLIst(itemList: ArrayList<ChoiceItem>) {
        itemList.sortBy { selecter(it) }
    }
}

