package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.Surface
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase

@Composable
fun Post(post: RedditPost, listener: RedditPostListener) {
    val type = GetPostTypeUseCase().execute(post.data)
    Card(
        colors = CardDefaults.cardColors(containerColor = Surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            PostHeader(post, listener, type)
            PostBody(post, listener, type)
            PostFooter(post, listener)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun PreviewPost() {
    Column(Modifier.background(Background)) {
        Post(TesterHelper.getPost(), RedditPostListener.preview())
    }
}
