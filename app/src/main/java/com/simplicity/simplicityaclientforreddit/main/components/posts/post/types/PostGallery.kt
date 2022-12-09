package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.Surface
import com.simplicity.simplicityaclientforreddit.main.theme.Transparent
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GalleryItem
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetGalleryImageUrlUseCase

@Composable
fun PostGallery(post: RedditPost, listener: RedditPostListener) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var position by remember { mutableStateOf(0) }
    val itemsInGallery = post.data.galleryData?.items?.size ?: 0
    var contentHeight by remember { mutableStateOf(300.dp) }
    // Show Left navigation
    Column {
        val galleryData = GetGalleryImageUrlUseCase().execute(post.data, position)
        Box {
            // ShowImage
            Log.i("PostGallery", "Showing gallery item with position $position")
            ShowImage(galleryData = galleryData, screenWidth) { contentHeight = it }
            // Navigation
            Row(Modifier.height(contentHeight)) {
                NavigationLeft(Modifier.weight(1f).fillMaxHeight()) {
                    if (position > 0) {
                        position -= 1
                    }
                }
                NavigationRight(Modifier.weight(1f).fillMaxHeight()) {
                    if ((position + 1) < itemsInGallery) {
                        position += 1
                    }
                }
            }
            // GalleryCounter
            val displayPosition = position + 1
            OnSurfaceText(Modifier.fillMaxWidth().padding(8.dp), textAlign = TextAlign.End, text = "$displayPosition/$itemsInGallery")
        }
        galleryData.current?.caption?.let { CText(modifier = Modifier.padding(8.dp).fillMaxWidth().background(Surface), text = it) }
    }
}

@Composable
fun NavigationLeft(modifier: Modifier, click: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier.background(Transparent).fillMaxWidth().clickable(interactionSource = interactionSource, indication = null) { click.invoke() }) {
        OnSurfaceText(text = "")
    }
}

@Composable
fun NavigationRight(modifier: Modifier, click: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(modifier.background(Transparent).fillMaxWidth().clickable(interactionSource = interactionSource, indication = null) { click.invoke() }) {
        OnSurfaceText(text = "")
    }
}

@Composable
fun ShowImage(galleryData: GalleryItem, screenWidth: Dp, contentHeightModified: (Dp) -> Unit) {
    var imageHeight = 0.dp
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
