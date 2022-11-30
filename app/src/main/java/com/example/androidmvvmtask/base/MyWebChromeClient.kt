package com.example.androidmvvmtask.base

import android.app.Activity
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast

class MyWebChromeClient(private val progress: ProgressBar) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress < 100) {
            progress.visibility = View.VISIBLE;
        }
        if (newProgress == 100) {
            progress.visibility = View.GONE;
        }
    }

}