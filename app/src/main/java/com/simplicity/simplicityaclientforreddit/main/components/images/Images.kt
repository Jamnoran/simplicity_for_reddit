package com.simplicity.simplicityaclientforreddit.main.components.images

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.simplicity.simplicityaclientforreddit.R
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

@Composable
fun CImageButton(iconResource: Int, click: () -> Unit) {
    Image(
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp)
            .clickable {
                click.invoke()
            },
        contentDescription = "",
        painter = painterResource(iconResource)
    )
}

@Composable
fun CImage(modifier: Modifier = Modifier, url: String, contentScale: ContentScale = ContentScale.FillWidth) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentScale = contentScale,
        contentDescription = "Image"
    )
}

@Composable
fun CImageZoomable(modifier: Modifier = Modifier, url: String, contentScale: ContentScale = ContentScale.FillWidth) {
    val scrollState: ScrollableState? = null
    var scale by remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(1f) }
    val offsetY = remember { mutableStateOf(1f) }

    val coroutineScope = rememberCoroutineScope()

    Box {
        AsyncImage(
            modifier = modifier
                .align(Alignment.TopCenter)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX.value,
                    translationY = offsetY.value
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scrollState?.run {
                            coroutineScope.launch {
                                setScrolling(false)
                            }
                        }
                        offsetX.value += pan.x
                        offsetY.value += pan.y
                        Log.i("Images", "Offset ${offsetX.value}x${offsetY.value} pan: ${pan.x}x${pan.y}")
                        scrollState?.run {
                            coroutineScope.launch {
                                setScrolling(true)
                            }
                        }
                        scale = when {
                            scale < 1f -> 1f
                            scale > 5f -> 5f
                            else -> scale * zoom
                        }
                    }
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentScale = contentScale,
            contentDescription = "Image"
        )
    }
}

suspend fun ScrollableState.setScrolling(value: Boolean) {
    scroll(scrollPriority = MutatePriority.PreventUserInput) {
        when (value) {
            true -> Unit
            else -> awaitCancellation()
        }
    }
}

@Composable
fun CImage(iconResource: Int) {
    Image(
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp),
        contentDescription = "",
        painter = painterResource(iconResource)
    )
}

@Preview
@Composable
fun Image() {
    Column(Modifier.fillMaxSize()) {
        CImageButton(iconResource = R.drawable.up_arrow_clicked) {}
        CImage(R.drawable.chat_icon)
        CImage(url = "")
    }
}
