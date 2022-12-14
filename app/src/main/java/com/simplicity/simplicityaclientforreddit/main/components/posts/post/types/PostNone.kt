package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.MarkDownText
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener

@Composable
fun PostNone(post: RedditPost, listener: RedditPostListener) {
    post.data.selftext?.let { selfText ->
        MarkDownText(modifier = Modifier.padding(8.dp), body = selfText, linkClicked = { listener.linkUrlClick(it) })
    }
}
