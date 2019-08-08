package com.example.whatsupq

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() { // 문제 키워드: 키보드 스크롤
    var finished = 0
    val PROGRESS_MAX = 6
    lateinit var checker: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        // 주석 처리된 친구들은 시도해보고 싶은데 에러떄문에 보류
//        var editTextArray = arrayListOf<EditText>(register_email, register_pw, register_phone, register_birth)
        checker = BooleanArray(4) { false }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        for(index in editTextArray.indices) {
//            editTextArray[index].setOnFocusChangeListener { view, hasFocus ->
//                if(!hasFocus) {
//                    checker[index] = editTextArray[index].text.isNotEmpty()
//                    applyProgress()
//                }
//            }
//        }
        //////////// 대체하고 싶은 코드
        register_email.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[0] = register_email.text.isNotEmpty()
                applyProgress()
            }
        }
        register_pw.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[1] = register_pw.text.isNotEmpty()
                applyProgress()
            }
        }
        register_phone.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[2] = register_phone.text.isNotEmpty()
                applyProgress()
            }
        }
        register_birth.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                checker[3] = register_birth.text.isNotEmpty()
                applyProgress()
            }
        }
        ///////////////
        register_birth.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                loseFocus()
                val imm = textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken,0)
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
    }

    private fun applyProgress() {
        var percent = arrayListOf<TextView>(
            register_pg0,
            register_pg1,
            register_pg2,
            register_pg3,
            register_pg4,
            register_pg5,
            register_pg6
        )
        finished = checkCount(checker)
        register_progrss.progress = finished

        for (index in percent.indices) {
            if (index == finished) {
                percent[index].setTextColor(Color.parseColor("#016a97"))
            } else {
                percent[index].setTextColor(Color.parseColor("#d1d3d4"))
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
