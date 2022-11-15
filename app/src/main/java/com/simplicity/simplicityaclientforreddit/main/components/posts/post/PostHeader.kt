package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.BodyNormalBold
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase

@Composable
fun PostHeader(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column(Modifier.padding(start = 8.dp, end = 8.dp)) {
        Row {
            CText(modifier = Modifier.clickable { listener.subredditClick.invoke(post) }, text = "r/${post.data.subreddit}", color = Primary)
            Spacer(Modifier.width(8.dp))
            OnSurfaceText(modifier = Modifier.clickable { listener.authorClick.invoke(post) }, text = " by u/${post.data.author ?: "[deleted]"}")
            Spacer(Modifier.width(8.dp))
            OnSurfaceText(text = " ${GetTimeAgoUseCase().execute(post.data.created)}")
        }
        val postType = GetPostTypeUseCase().execute(post.data).name
        OnSurfaceText(text = "{ $postType }")
        Spacer(Modifier.height(4.dp))
        Row {
            if (type != PostType.LINK) {
                OnSurfaceText(modifier = Modifier.padding(bottom = 4.dp).clickable { listener.redditClick.invoke(post) }, text = post.data.title ?: "[deleted]", style = BodyNormalBold)
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    PostHeader(post = TesterHelper.getPost(), listener = RedditPostListener.preview(), type = PostType.IMAGE)
}
