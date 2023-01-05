package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.PostBody
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase

@Composable
fun PostRepost(post: RedditPost, listener: RedditPostListener) {
    post.data.crosspostParentList?.first()?.let { repost ->
        Column(modifier = Modifier.padding(8.dp)) {
            CText("Repost from u/${repost.author} in r/${repost.subreddit}")
            CText(text = repost.title ?: "[Deleted]")
        }
        PostBody(
            post = RedditPost(data = repost),
            listener = listener,
            type = GetPostTypeUseCase().execute(repost)
        )
    }
}
