package com.example.whatsupq.ui.buy

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.whatsupq.BaseActivity
import com.example.whatsupq.R
import kotlinx.android.synthetic.main.activity_webview.*

class BuyDestFindActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        postView.setCallback { zoneCode, address, buildingName ->
            val intent = Intent(this, BuyActivity::class.java)
            intent.putExtra("zoneCode", "" + zoneCode)
            intent.putExtra("address", "" + address)
            intent.putExtra("buildingName", "" + buildingName)

            Log.e("myTag", "WebviewActivity: " + zoneCode)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        postView.setPostUrl("https://windsekirun.github.io/DaumPostCodeView/")
        postView.startLoad()
    }
}