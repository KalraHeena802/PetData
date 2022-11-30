package com.example.androidmvvmtask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.androidmvvmtask.base.MyWebChromeClient
import com.example.androidmvvmtask.base.MyWebViewClient
import com.example.androidmvvmtask.constant.AppConstant
import com.example.androidmvvmtask.databinding.ActivityPetDetailBinding

class PetDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Pet Details"


        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_pet_detail
        )

        webViewSetup()
        getIntentData()


    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        binding.webView.apply {
            webViewClient = MyWebViewClient(this@PetDetailActivity)
            webChromeClient = MyWebChromeClient(binding.progress)
            isVerticalScrollBarEnabled = true;
            requestFocus();
            settings.defaultTextEncodingName = "utf-8";
            settings.javaScriptEnabled = true;
        }

    }

    private fun getIntentData() {
        if (intent.extras != null) {
            if (intent.extras?.containsKey(AppConstant.CONTENT_URL)!!) {
                val contentUrl = intent.extras?.getString(AppConstant.CONTENT_URL, "")
                binding.webView.loadUrl(contentUrl!!)
            }

        }

    }


    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}