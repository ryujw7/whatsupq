package com.example.whatsupq

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

/**
 * Created by user on 2018-05-22.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    val actList : ArrayList<Activity>? = null
    val format = DecimalFormat("###,###")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (actList != null) {
            actList.clear()
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}