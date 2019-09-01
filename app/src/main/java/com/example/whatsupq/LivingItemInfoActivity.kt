package com.example.whatsupq

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_living_item_info.*
import kotlinx.android.synthetic.main.activity_loading.*
import java.lang.InterruptedException

class LivingItemInfoActivity : BaseActivity() {
    val handler = Handler()
    lateinit var anim : Animation
    lateinit var dialog : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_item_info)
        anim = AnimationUtils.loadAnimation(this,R.anim.loading)
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_loading)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.loading_img.animation = anim
        Thread(Runnable {
            run {
                handler.post(Runnable {
                    run {
                        dialog.show()
                    }
                })
                try {
                    Thread.sleep(3000)
                } catch (e : InterruptedException) {
                    e.printStackTrace()
                }
                handler.post(Runnable {
                    run {
                        fragment_living_item_info.addView(View.inflate(this, R.layout.fragment_living_item_info, null))
                        dialog.dismiss()
                    }
                })
            }
        }).start()
    }
}