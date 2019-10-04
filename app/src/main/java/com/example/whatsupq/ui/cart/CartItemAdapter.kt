package com.example.whatsupq.ui.cart

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.whatsupq.DB.CartDBHelper
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart_category.view.*
import kotlinx.android.synthetic.main.activity_cart_item.view.*
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class CartItemAdapter(val context: Context, val category_item_list: MutableMap<String, ArrayList<CartItem>>, val cartDBHelper: CartDBHelper) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_CATEGORY = 0
    val VIEW_TYPE_ITEM = 1
    var category_list = category_item_list.keys.toTypedArray()
    var category_index = arrayListOf<Int>()
    init {
        categoryPositionCalculate()
    }

    fun categoryRefresh() { // 카테고리 비어있을 시 카테고리 제거
        for (category in category_list) {
            if (category_item_list.getValue(category).size == 0) {
                category_item_list.remove(category)
            }
        }
        category_list = category_item_list.keys.toTypedArray()
        categoryPositionCalculate()
    }

    fun categoryPositionCalculate() { // 카테고리가 들어갈 position 구하기
        var tempIndex = 0
        category_index.clear()
        for (itemList in category_item_list.values) {
            category_index.add(tempIndex)
            tempIndex += itemList.size + 1
        }
    }

    fun isCategoryPosition(position: Int): Boolean { // 카테고리 position인지 확인
        return position in category_index
    }

    fun convertPosToCategoryIndex(position: Int): Int { // position을 카테고리 배열에 맞는 index로 변환
        return category_index.indexOf(position)
    }

    fun findCategoryIndexOfItem(position: Int): Int { // position을 통해서 item 의 카테고리(의 index) 찾기
        for (c_index in category_index.indices) {
            if (category_index[c_index] > position) {
                return c_index - 1
            }
        }
        return category_index.size - 1
    }

    fun positionForItem(position: Int): Int { // 카테고리 내부에서 아이템의 index 위치 찾기
        for (c_index in category_index.indices) {
            if (category_index[c_index] > position) {
                return position - category_index[c_index - 1] - 1
            }
        }
        return position - category_index.last() - 1
    }

    override fun getItemViewType(position: Int): Int {
        if (isCategoryPosition(position)) return VIEW_TYPE_CATEGORY
        else return VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        var size = 0
        for (itemList in category_item_list.values) {
            size += itemList.size
        }
        return size + category_item_list.keys.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_CATEGORY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_category, parent, false)
                return ViewHolder_category(context, view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_item, parent, false)
                return ViewHolder_item(context, view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder_category) {
            var item = category_list[convertPosToCategoryIndex(position)]
            holder.apply {
                bind(item)
            }
        } else if (holder is ViewHolder_item) {
            var categoryIndex = findCategoryIndexOfItem(position)
            var key = category_list[categoryIndex]
            var index = positionForItem(position)
            var item = category_item_list[key]!![index]
            holder.apply {
                bind(item)
                itemView.tag = item
                itemView.cart_item_frequency.setSelection(item.frequency - 1)
                itemView.cart_item_frequency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, spinnerPosition: Int, p3: Long) {
                        when (category_list[findCategoryIndexOfItem(position)]) {
                            "생필품" -> cartDBHelper.updateFrequency("CART_ESSENTIAL", item.product_id, spinnerPosition + 1)
                            "테마박스" -> cartDBHelper.updateFrequency("CART_BOX_THEME", item.product_id, spinnerPosition + 1)
                            else -> {
                            }
                        }
                        item.frequency = spinnerPosition + 1
                    }

                }
                itemView.cart_item_delete.setOnClickListener {
                    category_item_list[category_list[categoryIndex]]!!.removeAt(positionForItem(position))
                    when (category_list[findCategoryIndexOfItem(position)]) {
                        "생필품" -> cartDBHelper.removeAtCart("CART_ESSENTIAL", item.product_id)
                        "테마박스" -> cartDBHelper.removeAtCart("CART_BOX_THEME", item.product_id)
                        else -> {
                        }
                    }
                    Toast.makeText(context, "상품을 장바구니에서 제외했습니다.", Toast.LENGTH_SHORT).show()

                    categoryRefresh()
                    notifyDataSetChanged()
                    (context as CartActivity).refreshCost()
                }
                itemView.cart_item_plus.setOnClickListener {
                    item.amount++
                    item.total_cost = item.cost * item.amount

                    when (category_list[findCategoryIndexOfItem(position)]) {
                        "생필품" -> cartDBHelper.updateAmount("CART_ESSENTIAL", item.product_id, item.amount)
                        "테마박스" -> cartDBHelper.updateAmount("CART_BOX_THEME", item.product_id, item.amount)
                        else -> {
                        }
                    }
                    notifyDataSetChanged()
                    (context as CartActivity).refreshCost()
                }
                itemView.cart_item_minus.setOnClickListener {
                    item.amount--
                    item.total_cost = item.cost * item.amount

                    when (category_list[findCategoryIndexOfItem(position)]) {
                        "생필품" -> cartDBHelper.updateAmount("CART_ESSENTIAL", item.product_id, item.amount)
                        "테마박스" -> cartDBHelper.updateAmount("CART_BOX_THEME", item.product_id, item.amount)
                        else -> {
                        }
                    }

                    notifyDataSetChanged()
                    (context as CartActivity).refreshCost()
                }
            }
        }

    }

    class ViewHolder_category(val context: Context, v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: String) {
            view.cart_category.text = item
        }
    }

    class ViewHolder_item(val context: Context, v: View) : RecyclerView.ViewHolder(v) {
        val format = DecimalFormat("###,###")
        lateinit var data: JSONObject
        lateinit var imageRequest: ImageRequest
        lateinit var imgQueue: RequestQueue
        lateinit var queue : RequestQueue
        val MAXAMOUNT = 10
        private var view: View = v
        fun bind(item: CartItem) {
            val act = context as Activity
            val product_id = item.product_id
            imgQueue = Volley.newRequestQueue(context)
            queue = Volley.newRequestQueue(context)
            if(item.category == "1") {
                try {
                    val jsonObjectRequest = object : JsonObjectRequest(Method.GET,
                        "http://54.180.46.143:3000/api/product/regular/detail?product_id=$product_id", null,
                        Response.Listener {
                            var status = it.getString("status")
                            var isSuccess = it.getString("success")
                            var message = it.getString("message")
                            if (status.equals("200")) {
                                data = it.getJSONObject("data")
                                Log.e("data", data.toString())
                                Glide.with(view.context).load(data.getString("main_img")).into(view.cart_item_img)
                            }
                        }, Response.ErrorListener {
                            Log.e("error", "통신 오류")
                        }) {

                    }
                    queue.add(jsonObjectRequest)
                } catch (e: JSONException) {
                    Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                }
            } else if (item.category == "2") {
                try {
                    val jsonObjectRequest = object : JsonObjectRequest(Method.GET,
                        "http://54.180.46.143:3000/api/product/themebox/detail?themebox_id=$product_id", null,
                        Response.Listener {
                            var status = it.getString("status")
                            var isSuccess = it.getString("success")
                            var message = it.getString("message")
                            if (status.equals("200")) {
                                data = it.getJSONObject("data")
                                var imgArray = data.getJSONArray("img")
                                Log.e("data", data.toString())
                                Glide.with(view.context).load(imgArray[imgArray.length()-1].toString()).into(view.cart_item_img)
                            }
                        }, Response.ErrorListener {
                            Log.e("error", "통신 오류")
                        }) {

                    }
                    queue.add(jsonObjectRequest)
                } catch (e: JSONException) {
                    Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                }
            }
            item.total_cost = item.cost * item.amount
            view.cart_item_cost.text = format.format(item.total_cost)
            view.cart_item_check.text = item.name
            view.cart_item_plus.isEnabled = (item.amount < MAXAMOUNT)
            view.cart_item_plus.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    return
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    when (p0.toString().toInt()) {
                        1 -> itemView.cart_item_minus.isEnabled = false
                        MAXAMOUNT -> itemView.cart_item_plus.isEnabled = false
                        else -> {
                            itemView.cart_item_plus.isEnabled = true
                            itemView.cart_item_minus.isEnabled = true
                        }
                    }
                    return
                }

                override fun afterTextChanged(p0: Editable?) {
                    return
                }
            })
            view.cart_item_minus.isEnabled = (item.amount > 1)
            view.cart_item_amount.text = item.amount.toString()
            view.cart_item_check.setOnCheckedChangeListener(null)
            view.cart_item_check.isChecked = item.checked
            view.cart_item_check.setOnCheckedChangeListener { _, b ->
                item.checked = b
                if (!b) {
                    act.cart_checkall.isChecked = false
                }
                (context as CartActivity).refreshCost()
            }
            view.cart_item_delete.setOnClickListener {

            }

        }
    }
}