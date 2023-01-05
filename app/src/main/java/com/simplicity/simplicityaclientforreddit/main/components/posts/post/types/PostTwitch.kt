package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener

@Composable
fun PostTwitch(post: RedditPost, listener: RedditPostListener) {
    post.data.urlOverriddenByDest?.let {
        Column(
            modifier = Modifier.clickable {
                listener.linkExternalBrowserClick(it)
            }
        ) {
            post.data.media?.oembed?.thumbnail_url?.let { thumbnailUrl ->
                CImage(url = thumbnailUrl)
            }
            CText(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                textAlign = TextAlign.Center,
                text = "This is a twitch clip, click to start it"
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    Column {
        PostTwitch(post = TesterHelper.getPost(), listener = RedditPostListener.preview())
    }
}
