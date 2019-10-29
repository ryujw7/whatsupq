package com.example.whatsupq.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.whatsupq.R

class SpecialPlaceholderFragment : Fragment() {
    var itemList = arrayListOf<SpecialItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_special, container, false)
        val webView = root.findViewById<WebView>(R.id.webView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        settings.loadWithOverviewMode = true
        webView.loadUrl("https://www.aswemake.com")
        return root
    }

    fun newInstance(): SpecialPlaceholderFragment
    {
        val args = Bundle()

        val frag = SpecialPlaceholderFragment()
        frag.arguments = args

        return frag
    }
}