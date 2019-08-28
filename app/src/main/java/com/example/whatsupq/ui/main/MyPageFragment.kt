package com.example.whatsupq.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatsupq.LoginActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.mypage_notfound.view.*

        /**
         * A placeholder fragment containing a simple view.
         */
        class MyPageFragment : Fragment() { // 아마 파라미터는 수정되어야 할 것..
        var isLogin = false
        var REQUEST_LOGIN = 10
        var RESULT_SUCCESS = 200
        var RESULT_FAILED = 400
        fun MyPageFragment() {

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (isLogin) {
            return inflater.inflate(R.layout.fragment_mypage, container, false)
        } else {
            val root = inflater.inflate(R.layout.mypage_notfound, container, false)
            val intent = Intent(activity, LoginActivity::class.java)
            root.notFound_txt.text = "회원가입하고 나만의 큐를 확인해요"
            root.notFound_btn.text = "로그인 / 회원가입"
            root.notFound_btn.setOnClickListener {
                startActivityForResult(intent, REQUEST_LOGIN)
            }
            return root
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_SUCCESS) {
                isLogin = true
                fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
            } else if (resultCode == RESULT_FAILED) {
                isLogin = false
                fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): MyPageFragment {
            return MyPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}