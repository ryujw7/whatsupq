package com.example.whatsupq.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import com.example.whatsupq.ui.themebox.ThemeboxActivity
import kotlinx.android.synthetic.main.fragment_home_choiceall_banner_item.*

class HomeBannerItemFragment(res : Bitmap) : Fragment() {
    private val resId = res
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_choiceall_banner_item, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_banner.setImageBitmap(resId)
    }
}