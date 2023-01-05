package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface

@Composable
fun PostDeleted(post: RedditPost, listener: RedditPostListener) {
    CText(modifier = Modifier.padding(50.dp), text = "This post has been deleted", color = OnSurface)
}
