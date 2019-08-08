package com.example.whatsupq

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    var finished = 0
    val PROGRESS_MAX = 6
    lateinit var checker: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        checker = BooleanArray(4) { false }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_email.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                if (register_email.text.isNotEmpty()) {
                    checker[0] = true
                } else {
                    checker[0] = false
                }
                applyProgress()
            }
        }
        register_pw.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                if (register_pw.text.isNotEmpty()) {
                    checker[1] = true
                } else {
                    checker[1] = false
                }
                applyProgress()
            }
        }
        register_birth.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                if (register_birth.text.isNotEmpty()) {
                    checker[2] = true
                } else {
                    checker[2] = false
                }
                applyProgress()
            }
        }
        register_phone.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                if (register_phone.text.isNotEmpty()) {
                    checker[3] = true
                } else {
                    checker[3] = false
                }
                applyProgress()
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

    fun applyProgress() {
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

        for(index in percent.indices){
            if(index == finished) {
                percent[index].setTextColor(Color.parseColor("#016a97"))
            }
            else{
                percent[index].setTextColor(Color.parseColor("#d1d3d4"))
            }
        }
        if (finished == PROGRESS_MAX) {
            register_btn.isEnabled = true
        }
        else{
            register_btn.isEnabled = false
        }
    }

    fun loseFocus(){
        register_email.clearFocus()
        register_pw.clearFocus()
        register_birth.clearFocus()
        register_phone.clearFocus()
    }

    fun checkCount(list: BooleanArray): Int {
        var total = 0
        for (item in list) {
            if (item) total++
        }
        if (register_f.isChecked or register_m.isChecked) total++
        if (register_agree.isChecked) total++
        return total
    }
}
