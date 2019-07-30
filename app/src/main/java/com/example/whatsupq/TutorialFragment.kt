package com.example.whatsupq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.tutorial_page.*

class TutorialFragment(tutoText: String, resId: Int) : Fragment() {
    private val txt = tutoText
    private val rid = resId
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tutorial_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tutorial_txt.text = txt
        tutorial_img.setImageResource(rid)
    }
}