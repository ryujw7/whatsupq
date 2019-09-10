package com.example.whatsupq.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.fragment_home_choiceall_banner_item.*

class HomeBannerItemFragment(index : Int, res : Bitmap ) : Fragment() {
    private var resId = res
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_choiceall_banner_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_banner.setImageBitmap(resId)
    }
}