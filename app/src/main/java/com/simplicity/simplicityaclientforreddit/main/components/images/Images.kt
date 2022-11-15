package com.simplicity.simplicityaclientforreddit.main.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.simplicity.simplicityaclientforreddit.R

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
            .placeholder(R.drawable.banner_example)
            .build(),
        contentScale = contentScale,
        contentDescription = "Image"
    )
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
