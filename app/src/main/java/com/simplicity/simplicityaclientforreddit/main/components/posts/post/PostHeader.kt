package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.components.texts.MarkDownSimple
import com.simplicity.simplicityaclientforreddit.main.components.texts.MarkDownText
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.* // ktlint-disable no-wildcard-imports
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase

@Composable
fun PostHeader(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column(Modifier.padding(start = 8.dp, end = 8.dp)) {
        // Title
        Row(modifier = Modifier.padding(top = 8.dp)) {
            if (type != PostType.LINK) {
                MarkDownSimple(
                    modifier = Modifier.padding(bottom = 4.dp)
                        .clickable { listener.redditClick.invoke(post) },
                    body = post.data.title ?: "[deleted]",
                    style = SpanStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
        Row {
            // SubReddit
            CText(modifier = Modifier, text = "In", color = OnSurface)
            CText(
                modifier = Modifier.padding(start = 4.dp)
                    .clickable { listener.subredditClick.invoke(post) },
                text = "r/${post.data.subreddit}",
                color = Secondary
            )
            // Author
            CText(modifier = Modifier.padding(start = 8.dp), text = "by", color = OnSurface)
            CText(
                modifier = Modifier.padding(start = 4.dp)
                    .clickable { listener.authorClick.invoke(post) },
                text = "u/${post.data.author ?: "[deleted]"}",
                color = Primary
            )
        }
        // Created at
        Row {
            CText(
                text = GetTimeAgoUseCase().execute(post.data.created),
                color = OnSurface
            )
            if (post.data.over_18 == true) {
                CText(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "NSFW",
                    color = Red
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    PostHeader(
        post = TesterHelper.getPost(),
        listener = RedditPostListener.preview(),
        type = PostType.IMAGE
    )
}
