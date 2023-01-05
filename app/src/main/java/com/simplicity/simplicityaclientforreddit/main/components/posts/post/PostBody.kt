package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostDeleted
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostGallery
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostImage
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostImgur
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostLink
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostNone
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostRepost
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostTwitch
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostVideo
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostYoutube
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.DELETED
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.GALLERY
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.IMAGE
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.IMGUR_LINK
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.IS_VIDEO
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.LINK
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.NONE
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.REDDIT_REPOST
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.RICH_VIDEO
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.TOURNAMENT
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType.YOUTUBE
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener

@Composable
fun PostBody(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column {
        when (type) {
            LINK -> PostLink(post = post, listener = listener)
            GALLERY -> PostGallery(post, listener)
            IMAGE -> PostImage(post, listener)
            IS_VIDEO -> PostVideo(post = post, listener = listener)
            RICH_VIDEO -> PostVideo(post = post, listener = listener)
            TOURNAMENT -> listener.nextPost.invoke()
            IMGUR_LINK -> PostImgur(post = post, listener = listener)
            YOUTUBE -> PostYoutube(post, listener)
            NONE -> PostNone(post, listener)
            DELETED -> PostDeleted(post, listener)
            REDDIT_REPOST -> PostRepost(post, listener)
            PostType.TWITCH_LINK -> PostTwitch(post, listener)
        }
    }
}

@Preview
@Composable
fun PostBodyPreview() {
    PostBody(post = TesterHelper.getPost(), listener = RedditPostListener.preview(), IMAGE)
}
