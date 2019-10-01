package com.kotlinnews.demo

import android.app.AlertDialog
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_new_details.*
import java.util.*

class NewsDetails : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_details)

        alertDialog = SpotsDialog.Builder().setContext(this).build()
        alertDialog.show()

        //webview
        webView_news.settings.javaScriptEnabled = true
        webView_news.webChromeClient = WebChromeClient()
        webView_news.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                alertDialog.dismiss()
            }
        }

        if (intent != null)
            if (!intent?.getStringExtra("webURL")!!.isEmpty())
                webView_news.loadUrl(intent.getStringExtra("webURL"))

    }
}