package com.example.whatsupq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.tutorial_page.*

class TutorialAFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tutorial_page, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tutorial_txt.text = "어느 새 떨어져가는 물, 세제, 휴지 등 생활 필수품은 기본"
        tutorial_img.setImageResource(R.mipmap.ic_launcher_round)
    }
}
