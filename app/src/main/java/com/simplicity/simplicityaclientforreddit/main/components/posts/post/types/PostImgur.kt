package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener

@Composable
fun PostImgur(post: RedditPost, listener: RedditPostListener) {
    Column(Modifier.fillMaxWidth()) {
        AndroidView(factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                post.data.urlOverriddenByDest?.let { postUrl -> loadUrl(postUrl) }
            }
        })
    }
}
