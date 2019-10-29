package com.example.whatsupq.ui.main.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.DB.SharedPreferenceController
import com.example.whatsupq.MainActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_search_first.view.*
import kotlinx.android.synthetic.main.fragment_search_result_yes.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder

/**
 * A placeholder fragment containing a simple view.
 */
class SearchPlaceholderFragment : Fragment(), MainActivity.OnKeyBackPressedListener {

    lateinit var queue: RequestQueue
    var flag = 1
    var recentList = arrayListOf("최근", "검색", "어어", "어어", "아앙") // 테스트 용으로 우겨넣은 리스트
    var recommendList = arrayListOf("추천", "검색", "어어", "으어", "어어")
    var searchList = arrayListOf<SearchResultItem>()
    lateinit var data: JSONObject
    lateinit var livingItemJSONArray: JSONArray
    lateinit var searchResultItemAdapter: SearchResultItemAdapter
    lateinit var root: View
    lateinit var subroot1: View
    lateinit var subroot2: View
    lateinit var subroot3: View
    lateinit var mainActivity: MainActivity
    fun newInstance(): SearchPlaceholderFragment {
        val args = Bundle()

        val frag = SearchPlaceholderFragment()
        frag.arguments = args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_search, container, false)
        val fragmentAdapter = SearchListViewPagerAdapter(childFragmentManager, recentList, recommendList)
        subroot1 = inflater.inflate(R.layout.fragment_search_first, null)
        subroot2 = inflater.inflate(R.layout.fragment_search_result_no, null)
        subroot3 = inflater.inflate(R.layout.fragment_search_result_yes, null)
        subroot1.search_viewpager.adapter = fragmentAdapter
        root.search_under_content.addView(subroot1)
        root.search_bar.setOnClickListener {
            root.search_bar.isIconified = false
        }
        root.search_bar.setOnSearchClickListener {
            root.search_under_content.removeAllViews()
            (context as MainActivity).setOnBackKeyPressedListener(this)
        }
        root.search_bar.setOnCloseListener {
            root.search_under_content.removeAllViews()
            root.search_under_content.addView(subroot1)
            false
        }
        root.search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                root.search_bar.clearFocus()
                root.search_under_content.removeAllViews()
                searchList.clear()
                subroot3.search_result_recyclerview.adapter = searchResultItemAdapter
                try {
                    queue = Volley.newRequestQueue(context)
                    val jsonObjectRequest = object : JsonObjectRequest(
                        Method.GET,
                        "http://54.180.46.143:3000/api/product/regular?search=${URLEncoder.encode(
                            p0,
                            "utf-8"
                        )}&flag=$flag", null,
                        Response.Listener {
                            var status = it.getString("status")
                            var isSuccess = it.getString("success")
                            var message = it.getString("message")
                            if (status.equals("200")) {
                                data = it.getJSONObject("data")
                                livingItemJSONArray = data.getJSONArray("product")
                                for (i in 0 until livingItemJSONArray.length()) {
                                    searchList.add(
                                        SearchResultItem(
                                            livingItemJSONArray.getJSONObject(i).getString("name"),
                                            livingItemJSONArray.getJSONObject(i).getString("content"),
                                            livingItemJSONArray.getJSONObject(i).getString("product_id")
                                        )
                                    )
                                    searchResultItemAdapter.notifyDataSetChanged()
                                    root.search_under_content.removeAllViews()
                                    root.search_under_content.addView(subroot3)
                                }
                            } else {
                                Log.d("message : ", message)
                            }
                        }, Response.ErrorListener {
                            Log.e("error", "통신 오류")
                        }) {
                    }
                    queue.add(jsonObjectRequest)
                } catch (e: JSONException) {
                    Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                }
                searchResultItemAdapter = SearchResultItemAdapter(searchList)
                if(searchList.isEmpty()) {
                    root.search_under_content.removeAllViews()
                    root.search_under_content.addView(subroot2)
                } else {
                    root.search_under_content.removeAllViews()
                    root.search_under_content.addView(subroot3)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0!!.isEmpty()) {
                    root.search_under_content.removeAllViews()
                } else {
                    root.search_under_content.removeAllViews()
                    searchList.clear()
                    searchResultItemAdapter = SearchResultItemAdapter(searchList)
                    try {
                        queue = Volley.newRequestQueue(context)
                        val jsonObjectRequest = object : JsonObjectRequest(
                            Method.GET,
                            "http://54.180.46.143:3000/api/product/regular?search=${URLEncoder.encode(
                                p0,
                                "utf-8"
                            )}&flag=$flag", null,
                            Response.Listener {
                                var status = it.getString("status")
                                var isSuccess = it.getString("success")
                                var message = it.getString("message")
                                if (status.equals("200")) {
                                    data = it.getJSONObject("data")
                                    livingItemJSONArray = data.getJSONArray("product")
                                    for (i in 0 until livingItemJSONArray.length()) {
                                        searchList.add(
                                            SearchResultItem(
                                                livingItemJSONArray.getJSONObject(i).getString("name"),
                                                livingItemJSONArray.getJSONObject(i).getString("content"),
                                                livingItemJSONArray.getJSONObject(i).getString("product_id")
                                            )
                                        )
                                        searchResultItemAdapter.notifyDataSetChanged()
                                    }
                                } else {
                                    Log.d("message : ", message)
                                }
                            }, Response.ErrorListener {
                                Log.e("error", "통신 오류")
                            }) {
                        }
                        queue.add(jsonObjectRequest)
                    } catch (e: JSONException) {
                        Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
                    }
                    subroot3.search_result_recyclerview.adapter = searchResultItemAdapter
                    root.search_under_content.addView(subroot3)
                }
                return true
            }
        })
        root.search_tab.setupWithViewPager(root.search_viewpager)
        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as MainActivity).setOnBackKeyPressedListener(this)
    }

    override fun onBackKey() {
        mainActivity = activity as MainActivity
        if (!root.search_bar.isIconified) {
            root.search_bar.isIconified = true
        } else {
            (context as MainActivity).setOnBackKeyPressedListener(null)
            mainActivity.onBackPressed()
        }
    }
}