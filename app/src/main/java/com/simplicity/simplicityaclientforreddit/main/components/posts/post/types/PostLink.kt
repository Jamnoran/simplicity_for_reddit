package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.*

@Composable
fun PostLink(post: RedditPost, listener: RedditPostListener) {
    if (post.data.thumbnail == null || post.data.thumbnail!!.isEmpty()) {
        ShowPostLinkWithThumbnail(post, listener)
    } else {
        ShowPostLink(post, listener)
    }
}

@Composable
fun ShowPostLink(post: RedditPost, listener: RedditPostListener) {
    Column(Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp)) {
        CText(
            modifier = Modifier.fillMaxWidth().clickable { listener.linkClick.invoke(post) },
            text = "${post.data.title}",
            style = BodyNormalLink,
            LinkColor
        )
        OnSurfaceText(text = "from ${post.data.domain}")
    }
}

@Composable
fun ShowPostLinkWithThumbnail(post: RedditPost, listener: RedditPostListener) {
    Row(Modifier.fillMaxWidth()) {
        CText(
            modifier = Modifier.weight(1f),
            text = "${post.data.title}",
            style = BodyNormal,
            OnSurface
        )
        Spacer(Modifier.width(8.dp))
        Column(
            Modifier.width(140.dp).background(Primary)
                .clickable { listener.linkClick.invoke(post) }
        ) {
            // Preview image
            Box(Modifier.width(140.dp).height(80.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(post.data.thumbnail),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                )
            }
            // Domain
            post.data.domain?.let {
                OnSurfaceText(
                    Modifier.background(Surface).fillMaxWidth(),
                    text = it,
                    style = BodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun PostLinkPreview() {
    Column(Modifier.fillMaxWidth()) {
        ShowPostLinkWithThumbnail(post = TesterHelper.getPost(), listener = RedditPostListener.preview())
        ShowPostLink(post = TesterHelper.getPost(), listener = RedditPostListener.preview())
    }
}
