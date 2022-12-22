package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener

@Composable
fun PostImage(post: RedditPost, listener: RedditPostListener) {
    post.data.urlOverriddenByDest?.let { it ->
        if (it.contains(".gif")) {
            ShowGif(it)
        } else {
            post.data.url?.let { imageUrl -> ShowImage(imageUrl) { listener.fullScreen(post) } }
        }
    }
}

@Composable
fun ShowGif(it: String) {
    com.simplicity.simplicityaclientforreddit.main.Global.imageLoader?.let { imageLoader ->
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(it)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Image",
            imageLoader = imageLoader
        )
    } ?: Log.i("PostImage", "We do not have imageLoader, can not show gifs!")
}

@Composable
fun ShowImage(it: String, onClick: () -> Unit) {
//    AsyncImage(
//        modifier = Modifier.fillMaxWidth(),
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(it)
//            .crossfade(true)
//            .build(),
//        contentScale = ContentScale.FillWidth,
//        contentDescription = "Image"
//    )
    CImage(
        modifier = Modifier.fillMaxWidth()
            .clickable(interactionSource = MutableInteractionSource(), indication = null) { onClick.invoke() },
        url = it
    )
}
