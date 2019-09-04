package com.example.whatsupq.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.DB.SharedPreferenceController
import com.example.whatsupq.LoginActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import kotlinx.android.synthetic.main.mypage_notfound.view.*
import org.json.JSONException
import org.json.JSONObject

/**
 * A placeholder fragment containing a simple view.
 */
class MyPageFragment : Fragment() { // 아마 파라미터는 수정되어야 할 것..
    var isLogin = false
    private var REQUEST_LOGIN = 10
    private var RESULT_SUCCESS = 200
    private var RESULT_FAILED = 400
    lateinit var token : String
    lateinit var queue : RequestQueue
    lateinit var status : String
    lateinit var isSuccess : String
    lateinit var message : String
    lateinit var data : JSONObject
    fun MyPageFragment() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root= inflater.inflate(R.layout.mypage_notfound, container, false)
        token = SharedPreferenceController.getUserToken(root.context.applicationContext)
        Log.e("token : ", token)
        isLogin = token != ""
        if (isLogin) {
            root = inflater.inflate(R.layout.fragment_mypage, container, false)
            try {
                queue = Volley.newRequestQueue(context)
                val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST,
                    "http://54.180.46.143:3000/api/mypage/user",null,
                    Response.Listener {
                        status = it.getString("status")
                        isSuccess = it.getString("success")
                        message = it.getString("message")
                        if (status.equals("200")) {
                            val intent = Intent()
                            data = it.getJSONObject("data")
                            root.mypage_name.text = data.getString("name")
                            root.mypage_email.text = data.getString("email")
                            SharedPreferenceController.setUserToken(context!!.applicationContext, token)
                        } else {
                            Toast.makeText(context!!.applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                        token = ""
                        Log.e("error", "통신 오류")
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val hashMap = HashMap<String, String>()
                        hashMap["token"] = token
                        return hashMap
                    }
                }
                queue.add(jsonObjectRequest)
            } catch (e: JSONException) {
                Log.d("JSON 오류 : ", "JSON이 비어있거나 삽입할 수 없음")
            }
            root.mypage_logout_btn.setOnClickListener {
                isLogin = false
                SharedPreferenceController.clearUserToken(root.context.applicationContext)
                Log.e("token : ", token)
                root= inflater.inflate(R.layout.mypage_notfound, container, false)
                Toast.makeText(context!!.applicationContext, token, Toast.LENGTH_SHORT).show()
                fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
            }
        } else {
            val intent = Intent(activity, LoginActivity::class.java)
            root.notFound_txt.text = "회원가입하고 나만의 큐를 확인해요"
            root.notFound_btn.text = "로그인 / 회원가입"
            root.notFound_btn.setOnClickListener {
                startActivityForResult(intent, REQUEST_LOGIN)
            }
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_SUCCESS) {
                isLogin = true
                fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
            } else if (resultCode == RESULT_FAILED) {
                isLogin = false
                fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
            }
        }
    }
    fun newInstance(): MyPageFragment
    {
        val args = Bundle()

        val frag = com.example.whatsupq.ui.main.MyPageFragment()
        frag.arguments = args

        return frag
    }
}