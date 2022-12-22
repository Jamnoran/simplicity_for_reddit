package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.components.web.CWebView
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.BodyNormal
import com.simplicity.simplicityaclientforreddit.main.theme.BodyNormalLink
import com.simplicity.simplicityaclientforreddit.main.theme.BodySmall
import com.simplicity.simplicityaclientforreddit.main.theme.LinkColor
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.Surface

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
        post.data.url?.let {
            Spacer(modifier = Modifier.height(16.dp))
            if (SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_SHOW_LINK_IN_WEB_VIEW_UNDER_POST, true)) {
                CWebView(url = it)
            }
        }
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
