package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.Transparent
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetGalleryImageUrlUseCase

@Composable
fun PostGallery(post: RedditPost, listener: RedditPostListener) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var position by remember { mutableStateOf(0) }
    val itemsInGallery = post.data.galleryData?.items?.size ?: 0
    var contentHeight by remember { mutableStateOf(300.dp) }
    // Show Left navigation
    Box {
        // ShowImage
        ShowImage(post = post, position = position, screenWidth) { contentHeight = it }
        // Navigation
        Row(Modifier.height(contentHeight)) {
            NavigationLeft(Modifier.weight(1f).fillMaxHeight()) { if (position > 0) { position -= 1 } }
            NavigationRight(Modifier.weight(1f).fillMaxHeight()) { if ((position + 1) < itemsInGallery) { position += 1 } }
        }
        val displayPosition = position + 1
        OnSurfaceText(Modifier.fillMaxWidth(), textAlign = TextAlign.End, text = "$displayPosition/$itemsInGallery")
    }
}

@Composable
fun NavigationLeft(modifier: Modifier, click: () -> Unit) {
    Box(modifier.background(Transparent).fillMaxWidth().clickable { click.invoke() }) {
        OnSurfaceText(text = "")
    }
}

@Composable
fun NavigationRight(modifier: Modifier, click: () -> Unit) {
    Box(modifier.background(Transparent).fillMaxWidth().clickable { click.invoke() }) {
        OnSurfaceText(text = "")
    }
}

@Composable
fun ShowImage(post: RedditPost, position: Int, screenWidth: Dp, contentHeightModified: (Dp) -> Unit) {
    Log.i("PostGallery", "Showing gallery item with position $position")
    var imageHeight = 0.dp
    val galleryData = GetGalleryImageUrlUseCase().execute(post.data, position)
    galleryData.current?.let { mediaData ->
        mediaData.imageRatio?.let { ratio ->
            imageHeight = screenWidth.div(ratio)
        }
        contentHeightModified.invoke(imageHeight)
        Log.i("PostGallery", "Calculating height with ${mediaData.imageRatio} and width $screenWidth resulting in $imageHeight")
        Image(
            painter = rememberAsyncImagePainter(mediaData.mediaUrl),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(imageHeight)
        )
        // TODO: Add content description
    }
}

@Preview
@Composable
fun PostGalleryPreview() {
    Column(Modifier.fillMaxWidth()) {
        PostGallery(post = TesterHelper.getPost(), listener = RedditPostListener.preview())
    }
}
