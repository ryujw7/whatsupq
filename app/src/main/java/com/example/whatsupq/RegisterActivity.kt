package com.example.whatsupq

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : BaseActivity() { // 문제 키워드: 키보드 스크롤
    var finished = 0
    val PROGRESS_MAX = 7
    lateinit var checker: BooleanArray
    var name = ""
    var email = ""
    var password = ""
    var phone = ""
    var birthday = ""
    var gender = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        // 주석 처리된 친구들은 시도해보고 싶은데 에러떄문에 보류
//        var editTextArray = arrayListOf<EditText>(register_email, register_pw, register_phone, register_birth)
        checker = BooleanArray(5) { false }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        back_btn.setOnClickListener {
            finish()
        }
        var jsonObject: JSONObject
        var queue: RequestQueue
        var status: String
        var isSuccess: String
        var message: String
        queue = Volley.newRequestQueue(this.applicationContext)
//        for(index in editTextArray.indices) {
//            editTextArray[index].setOnFocusChangeListener { view, hasFocus ->
//                if(!hasFocus) {
//                    checker[index] = editTextArray[index].text.isNotEmpty()
//                    applyProgress()
//                }
//            }
//        }
        //////////// 대체하고 싶은 코드
        register_name.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[0] = register_name.text.isNotEmpty()
                applyProgress()
            }
        }
        register_email.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[1] = register_email.text.isNotEmpty()
                applyProgress()
            }
        }
        register_pw.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[2] = register_pw.text.isNotEmpty()
                applyProgress()
            }
        }
        register_phone.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[3] = register_phone.text.isNotEmpty()
                applyProgress()
            }
        }
        register_birth.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[4] = register_birth.text.isNotEmpty()
                applyProgress()
            }
        }
        ///////////////
        register_birth.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                loseFocus()
                val imm = textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }
        register_m.setOnCheckedChangeListener { compoundButton, checked ->
            loseFocus()
            applyProgress()
        }
        register_f.setOnCheckedChangeListener { compoundButton, checked ->
            loseFocus()
            applyProgress()
        }
        register_agree.setOnCheckedChangeListener { compoundButton, checked ->
            loseFocus()
            applyProgress()
        }
        register_btn.setOnClickListener {
            name = register_name.text.toString()
            email = register_email.text.toString()
            password = register_pw.text.toString()
            phone = register_phone.text.toString()
            birthday = register_birth.text.toString()
            if (register_f.isChecked) {
                gender = "0"
            } else if (register_m.isChecked) {
                gender = "1"
            }
            try {
                queue = Volley.newRequestQueue(applicationContext)
                jsonObject = JSONObject()
                jsonObject.accumulate("email", email)
                jsonObject.accumulate("password", password)
                jsonObject.accumulate("name", name)
                jsonObject.accumulate("birth", birthday)
                jsonObject.accumulate("phone", phone)
                jsonObject.accumulate("gender", gender)
                val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,
                    "http://54.180.46.143:3000/api/auth/signup", jsonObject,
                    Response.Listener {
                        status = it.getString("status")
                        isSuccess = it.getString("success")
                        message = it.getString("message")
                        if (status.equals("201")) {
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
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

    private fun applyProgress() {
        var percent = arrayListOf<TextView>(
            register_pg0,
            register_pg1,
            register_pg2,
            register_pg3,
            register_pg4,
            register_pg5,
            register_pg6,
            register_pg7
        )
        finished = checkCount(checker)
        register_progrss.progress = finished

        for (index in percent.indices) {
            if (index == finished) {
                percent[index].visibility = View.VISIBLE
            } else {
                percent[index].visibility = View.INVISIBLE
            }
        }
        register_btn.isEnabled = (finished == PROGRESS_MAX)
    }

    private fun loseFocus() {
        register_email.clearFocus()
        register_pw.clearFocus()
        register_birth.clearFocus()
        register_phone.clearFocus()
    }

    private fun checkCount(list: BooleanArray): Int {
        var total = 0
        for (item in list) {
            if (item) total++
        }
        if (register_f.isChecked or register_m.isChecked) total++
        if (register_agree.isChecked) total++
        return total
    }
}
