package com.simplicity.simplicityaclientforreddit.main.components.web

import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CWebView(modifier: Modifier = Modifier, url: String) {
    var visibleLoading = true
    Log.i("CWebView", "Showing url $url")
    class CustomWebViewClient : WebViewClient() {

        // Load the URL
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            Log.i("CWebView", "onPageFinished")
            if (url.contains("imgur.com") || url.contains("i.imgur.com")) {
                view.scrollTo(0, 140)
            }
            visibleLoading = false
        }
    }

    val customWebViewClient = CustomWebViewClient()

    Column(modifier.fillMaxWidth()) {
        AndroidView(factory = {
            ProgressBar(it).apply {
                visibility = if (visibleLoading) View.VISIBLE else View.GONE
            }
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.domStorageEnabled = true
                webViewClient = customWebViewClient
            }
        }, update = { webView -> webView.loadUrl(url) })
    }
}

@Composable
fun CWebViewHtmlData(modifier: Modifier = Modifier, html: String) {
    var visibleLoading = true

    class CustomWebViewClient : WebViewClient() {

        // Load the URL
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            visibleLoading = false
        }
    }

    val customWebViewClient = CustomWebViewClient()

    val mimeType = "text/html"
    val encoding = "UTF-8"

    Column(modifier.fillMaxWidth()) {
//        val context = LocalContext.current
//        val state = rememberWebViewState(url)
//        WebView(
//            context
//        ).loadUrl(url)

        AndroidView(factory = {
            ProgressBar(it).apply {
                visibility = if (visibleLoading) View.VISIBLE else View.GONE
            }
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.domStorageEnabled = true
                webViewClient = customWebViewClient
            }
        }, update = { webView -> webView.loadDataWithBaseURL("", html, mimeType, encoding, "") })
    }
}
