package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.CRoundedBottomCorners
import com.simplicity.simplicityaclientforreddit.main.theme.CRoundedCorners
import com.simplicity.simplicityaclientforreddit.main.theme.Surface
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase

@Composable
fun Post(post: RedditPost, listener: RedditPostListener, singlePost: Boolean = true) {
    val type = GetPostTypeUseCase().execute(post.data)
    Card(
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = if (singlePost) CRoundedBottomCorners() else CRoundedCorners()
    ) {
        if (type == PostType.NONE) {
            // Post like NONE can contain long texts so we want to show title first
            PostAtBottom(post, listener, type)
        } else {
            // Post like images and videos should have the title under it for cooler effect
            PostAtTop(post, listener, type)
        }
    }
}

@Composable
fun PostAtBottom(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column(Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        PostHeader(post.data, listener, type)
        PostBody(post, listener, type)
        ShowSelfText(type, post)
        PostFooter(post, listener)
    }
}

@Composable
fun PostAtTop(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column(Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        PostBody(post, listener, type)
        ShowSelfText(type, post)
        PostHeader(post.data, listener, type)
        PostFooter(post, listener)
    }
}

@Composable
fun ShowSelfText(type: PostType, post: RedditPost) {
    if (type != PostType.NONE && post.data.selftext?.isNotBlank() == true) { // Description text
        CText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Surface),
            text = "${post.data.selftext}"
        )
    }
}

@Preview
@Composable
fun PreviewPost() {
    Column(Modifier.background(Background)) {
        Post(TesterHelper.getPost(), RedditPostListener.preview())
    }
}

@Preview
@Composable
fun PreviewPostNone() {
    Column(Modifier.background(Background)) {
        Post(TesterHelper.getPost(null), RedditPostListener.preview())
    }
}
