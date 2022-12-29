package com.simplicity.simplicityaclientforreddit.main.components.web

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CWebView(modifier: Modifier = Modifier, url: String) {
    Column(modifier.fillMaxWidth()) {
        AndroidView(factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
//                isVerticalScrollBarEnabled = true
//                isHorizontalScrollBarEnabled = true
                loadUrl(url)
            }
        })
    }
}
