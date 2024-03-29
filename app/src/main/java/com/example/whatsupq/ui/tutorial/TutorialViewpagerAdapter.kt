package com.example.whatsupq.ui.tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.whatsupq.R

class TutorialViewpagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val text = arrayListOf("어느 새 떨어져가는 물, 세제, 휴지 등 생활 필수품은 기본","혼술, 혼밥에 친환경 마스크팩도 정기배송 받아 볼 수 있어요.","What's sub?\n왓섭큐가 도와줄게요.")
    val res = arrayListOf(R.drawable.tutorial_img_1,R.drawable.tutorial_img_2,R.drawable.tutorial_img_3)
    override fun getItem(position: Int): Fragment? {
        return TutorialFragment(text[position], res[position])
    }

    override fun getCount() = text.size

}