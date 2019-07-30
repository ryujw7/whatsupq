package com.example.whatsupq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.tutorial_page.*

class TutorialBFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tutorial_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tutorial_txt.text = "혼술, 혼밥에 친환경 마스크팩도 정기배송 받아 볼 수 있어요."
        tutorial_img.setImageResource(R.mipmap.ic_launcher)
    }
}