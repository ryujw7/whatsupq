package com.example.whatsupq

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.whatsupq.DB.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    lateinit var login_id: EditText
    lateinit var login_password: EditText
    lateinit var login_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_id = findViewById(R.id.login_id)
        login_password = findViewById(R.id.login_password)
        login_btn = findViewById(R.id.login_btn)
        var id: String? = null
        var password: String? = null
        var jsonObject: JSONObject
        var queue: RequestQueue
        var result: JSONObject
        var status: String
        var isSuccess: String
        var message: String
        var data: JSONObject
        queue = Volley.newRequestQueue(this.applicationContext)
        find_id_btn.setOnClickListener {
            startActivity(Intent(this, FindIDActivity::class.java))
        }
        find_pw_btn.setOnClickListener {
            startActivity(Intent(this, FindPWActivity::class.java))
        }
        find_register_btn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        login_btn.setOnClickListener {
            if (login_id.text.toString().length != 0 && login_password.text.toString().length != 0) {
                id = login_id.text.toString()
                password = login_password.text.toString()
            } else {
                Toast.makeText(applicationContext, "아이디 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                queue = Volley.newRequestQueue(applicationContext)
                jsonObject = JSONObject()
                jsonObject.accumulate("email", id)
                jsonObject.accumulate("password", password)
                val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,
                    "http://54.180.46.143:3000/api/auth/signin", jsonObject,
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
        }
    }
}
