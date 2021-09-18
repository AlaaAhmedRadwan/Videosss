package com.example.videosdownloader.presentaion.webview

import android.os.Bundle
import android.os.FileUtils
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.videosdownloader.R
import kotlinx.android.synthetic.main.web_view.*
import android.content.Intent
import com.example.videosdownloader.model.MainDataItem
import com.example.videosdownloader.presentaion.ClickHandler
import android.webkit.WebView
import android.view.View


class WebViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        val extras = intent.extras

        val url = extras?.getParcelable<MainDataItem>("int_value")



        val webview = findViewById<View>(R.id.webView) as WebView
        webview.settings.javaScriptEnabled = true
        val pdf = url?.url

        webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$pdf")

        webView.requestFocus();


        val webSettings = webView!!.getSettings()
        webSettings.setJavaScriptEnabled(true)
        // Force links and redirects to open in the WebView instead of in a browser
        webView!!.setWebViewClient(WebViewClient())
    }
}