package com.example.whatsupq.ui.curation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
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
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.R
import com.example.whatsupq.ui.themebox.ThemeboxActivity
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.fragment_curation_init.view.*
import kotlinx.android.synthetic.main.fragment_curation_result.view.*
import kotlinx.android.synthetic.main.fragment_curation_set.view.*
import kotlinx.android.synthetic.main.fragment_home_themebox_item.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder
import java.text.DecimalFormat

/**
 * A placeholder fragment containing a simple view.
 */
class CurationPlaceholderFragment : Fragment(), CurationPriorityAdapter.OnStartDragListener {

    lateinit var root: View

    lateinit var mItemTouchHelper: ItemTouchHelper
    val priorityList = arrayListOf("수면", "먹는것", "인테리어", "옷관리", "청소")
    private var essentialProdList = ArrayList<ProdInfo>()
    private var optionalProdList = ArrayList<ProdInfo>()
    lateinit var firstList: RecyclerView
    lateinit var secondList: RecyclerView
    lateinit var mAdapter1: CurationResultAdapter
    lateinit var mAdapter2: CurationResultAdapter
    lateinit var themebox: View
    lateinit var dialog: Dialog
    lateinit var anim: Animation
    private val handler = Handler()

    lateinit var curationSetting: SharedPreferences
    lateinit var curationEditor: SharedPreferences.Editor
    private var pageState: String? = null
    private var curationSettingFlag = 0
    val format = DecimalFormat("###,###")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        curationSetting = context!!.getSharedPreferences("setting", Context.MODE_PRIVATE)
        curationEditor = curationSetting.edit()
        pageState = curationSetting.getString("PAGESTATE", "INITIAL")
        when (pageState) {
            "INITIAL" -> {
                root = inflater.inflate(R.layout.fragment_curation_init, container, false)
                root.curation_init_click.setOnClickListener {
                    curationEditor.putString("PAGESTATE", "SETTING")
                    curationEditor.apply()
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
            "SETTING" -> {
                root = inflater.inflate(R.layout.fragment_curation_set, container, false)
                root.curation_set_click.isEnabled = false
                root.curation_set_range.setOnRangeSeekbarChangeListener { minValue, maxValue ->
                    root.curation_set_range_min.text = format.format(minValue) + '원'
                    root.curation_set_range_max.text = format.format(maxValue) + '원'
                }
                val priorityAdapter = CurationPriorityAdapter(this, priorityList)
                val mCallback = CurationPriorityTouchHelperCallback(priorityAdapter)
                mItemTouchHelper = ItemTouchHelper(mCallback)
                mItemTouchHelper.attachToRecyclerView(root.curation_set_priority_recycerview)
                root.curation_set_priority_recycerview.adapter = priorityAdapter
                root.curation_set_room_type.setOnCheckedChangeListener { radioGroup, i ->
                    val current = root.findViewById<RadioButton>(i).tag
                    when (current) {
                        "room_type_1" -> {
                        }
                        "room_type_2" -> {
                        }
                        "room_type_3" -> {
                        }
                        "room_type_4" -> {
                        }
                        else -> {
                        }
                    }
                    curationSettingFlag = curationSettingFlag or 1
                    if (curationSettingFlag == 3) {
                        root.curation_set_click.isEnabled = true
                    }

                }
                root.curation_set_room_year.setOnCheckedChangeListener { radioGroup, i ->
                    val current = root.findViewById<RadioButton>(i).tag
                    when (current) {
                        "room_year_1" -> curationEditor.putString("ROOM_PERIOD", "1")
                        "room_year_2" -> curationEditor.putString("ROOM_PERIOD", "2")
                        "room_year_3" -> curationEditor.putString("ROOM_PERIOD", "3")
                        "room_year_4" -> curationEditor.putString("ROOM_PERIOD", "4")
                        else -> {
                        }
                    }
                    curationEditor.apply()
                    curationSettingFlag = curationSettingFlag or 2
                    if (curationSettingFlag == 3) {
                        root.curation_set_click.isEnabled = true
                    }
                }
                root.curation_set_click.setOnClickListener {
                    curationEditor.putString("PRICE_MIN", root.curation_set_range_min.text.toString())
                    curationEditor.putString("PRICE_MAX", root.curation_set_range_max.text.toString())
                    curationEditor.putString("CATEGORY_FIRST", priorityAdapter.getItemText(0))
                    curationEditor.putString("CATEGORY_SECOND", priorityAdapter.getItemText(1))
                    curationEditor.putString("CATEGORY_LAST", priorityAdapter.getItemText(4))
                    curationEditor.putString("PAGESTATE", "RESULT")
                    curationEditor.apply()
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
            "RESULT" -> {
                root = inflater.inflate(R.layout.fragment_curation_result, container, false)
                val userType = curationSetting.getString("ROOM_PERIOD", "ERROR")
                root.curation_result_yourType.text = when (userType) {
                    "1" -> "준비중인 타입 텍스트"
                    "2" -> "1년 미만 타입 텍스트"
                    "3" -> "중간 타입 텍스트"
                    "4" -> "자취 오래한 타입 텍스트"
                    else -> "엥 이게 나올리가 없는데"
                }
                mAdapter1 = CurationResultAdapter(essentialProdList)
                mAdapter2 = CurationResultAdapter(optionalProdList)
                getBitmap()
                themebox = root.findViewById(R.id.fragment_home_theme_curation)
                firstList = root.findViewById(R.id.curation_result_essential_recyclerview)
                secondList = root.findViewById(R.id.curation_result_optional_recyclerview)
                anim = AnimationUtils.loadAnimation(root.context, R.anim.loading)
                dialog = Dialog(root.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.activity_loading)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.loading_img.animation = anim
                root.curation_result_essential_recyclerview.adapter = CurationResultAdapter(essentialProdList)
                root.curation_result_optional_recyclerview.adapter = CurationResultAdapter(optionalProdList)
                root.curation_result_click.setOnClickListener {
                    curationEditor.putString("PAGESTATE", "SETTING")
                    curationEditor.apply()
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
                Thread(Runnable {
                    run {
                        handler.post {
                            run {
                                dialog.show()
                            }
                        }
                        try {
                            Thread.sleep(1200)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        handler.post {
                            run {
                                firstList.adapter = mAdapter1
                                secondList.adapter = mAdapter2
                                firstList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                                secondList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                                dialog.dismiss()
                            }
                        }
                    }
                }).start()
            }
            else -> {
                root = inflater.inflate(R.layout.fragment_curation_init, container, false)
                root.curation_init_click.setOnClickListener {
                    curationEditor.putString("PAGESTATE", "SETTING")
                    curationEditor.apply()
                    fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
                }
            }
        }

        return root
    }

    override fun onStartDrag(holder: CurationPriorityAdapter.ViewHolder) {
        mItemTouchHelper.startDrag(holder)
    }

    private fun selecter(prodInfo: ProdInfo): Int = prodInfo.index
    private fun sortLIst(itemList: ArrayList<ProdInfo>) {
        itemList.sortBy { selecter(it) }
    }

    fun getBitmap() {
        lateinit var regularJsonArray: JSONArray
        lateinit var regularNotJsonArray: JSONArray
        lateinit var themeBoxJsonObject: JSONObject
        lateinit var data: JSONObject
        lateinit var imageRequest: ImageRequest
        lateinit var themebox_id: String
        val imgQueue = Volley.newRequestQueue(context)
        val queue = Volley.newRequestQueue(context)
        if (essentialProdList.isNotEmpty()) {
            essentialProdList.clear()
        }
        if (optionalProdList.isNotEmpty()) {
            optionalProdList.clear()
        }
        try {
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
                "http://54.180.46.143:3000/api/product/custom?first=${URLEncoder.encode(
                    firstCategory,
                    "utf-8"
                )}&second=${URLEncoder.encode(
                    secondCategory,
                    "utf-8"
                )}&fifth=${URLEncoder.encode(
                    lastCategory,
                    "utf-8"
                )}&minprice=$minPrice&maxprice=$maxPrice", null,
                Response.Listener {
                    var status = it.getString("status")
                    var isSuccess = it.getString("success")
                    var message = it.getString("message")
                    if (status.equals("200")) {
                        data = it.getJSONObject("data")
                        Log.e("dataset : ", data.toString())
                        regularJsonArray = data.getJSONArray("regularity")
                        regularNotJsonArray = data.getJSONArray("regular_not_Important")
                        themeBoxJsonObject = data.getJSONObject("themeboxes")
                        for (j in 0 until regularJsonArray.length()) {
                            var id = regularJsonArray.getJSONObject(j).getString("product_id")
                            try {
                                imageRequest = ImageRequest(
                                    regularJsonArray.getJSONObject(j).getString("main_img"),
                                    Response.Listener<Bitmap> { response ->
                                        essentialProdList.add(
                                            ProdInfo(
                                            j,
                                            id,
                                            response,
                                            regularJsonArray.getJSONObject(j).getString("name"),
                                            regularJsonArray.getJSONObject(j).getString("saled_price") + "원")
                                        )
                                        sortLIst(essentialProdList)
                                        mAdapter1.notifyDataSetChanged()
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
                        for (k in 0 until regularNotJsonArray.length()) {
                            var id = regularNotJsonArray.getJSONObject(k).getString("product_id")
                            try {
                                imageRequest = ImageRequest(
                                    regularNotJsonArray.getJSONObject(k).getString("main_img"),
                                    Response.Listener<Bitmap> { response ->
                                        optionalProdList.add(ProdInfo(
                                            k,
                                            id,
                                            response,
                                            regularNotJsonArray.getJSONObject(k).getString("name"),
                                            regularNotJsonArray.getJSONObject(k).getString("saled_price") + "원"))
                                        sortLIst(optionalProdList)
                                        mAdapter2.notifyDataSetChanged()
                                    }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
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
                        try {
                            themebox_id = themeBoxJsonObject.getString("themebox_id")
                            imageRequest = ImageRequest(
                                themeBoxJsonObject.getString("main_img"),
                                Response.Listener<Bitmap> { response ->
                                    val float = 12
                                    themebox.theme_image.scaleType = ImageView.ScaleType.CENTER_CROP
                                    themebox.theme_image.setColorFilter(
                                        Color.parseColor("#BDBDBD"),
                                        PorterDuff.Mode.MULTIPLY
                                    )
                                    themebox.theme_image.setImageBitmap(response)
                                    themebox.theme_image.clipToOutline = true
                                    themebox.theme_image.setOnClickListener {
                                        val intent = Intent(context, ThemeboxActivity::class.java)
                                        intent.putExtra("themebox_id", themebox_id)
                                        startActivity(intent)
                                    }
                                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                                Response.ErrorListener {
                                    Log.e("error", "통신 오류")
                                }
                            )
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
    fun newInstance(): CurationPlaceholderFragment {
        val args = Bundle()

        val frag = CurationPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}