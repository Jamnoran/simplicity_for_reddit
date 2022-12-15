package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostGallery
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostImage
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostImgur
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostLink
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostNone
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostVideo
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.types.PostYoutube
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface

@Composable
fun PostBody(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column() {
        when (type) {
            PostType.LINK -> {
                PostLink(post = post, listener = listener)
            }
            PostType.GALLERY -> {
                PostGallery(post, listener)
            }
            PostType.IMAGE -> {
                PostImage(post, listener)
            }
            PostType.IS_VIDEO -> {
                PostVideo(post = post, listener = listener)
            }
            PostType.RICH_VIDEO -> {
                PostVideo(post = post, listener = listener)
            }
            PostType.TOURNAMENT -> {
//                binding.root.visibility = View.GONE
                listener.nextPost.invoke()
            }
            PostType.IMGUR_LINK -> {
                PostImgur(post = post, listener = listener)
            }
            PostType.YOUTUBE -> {
                PostYoutube(post, listener)
            }
            PostType.NONE -> {
                PostNone(post, listener)
            }
            PostType.DELETED -> {
                PostDeleted(post, listener)
            }
        }
    }
}

@Composable
fun PostDeleted(post: RedditPost, listener: RedditPostListener) {
    CText(text = "This post has been deleted", color = OnSurface)
}

@Preview
@Composable
fun PostBodyPreview() {
    PostBody(post = TesterHelper.getPost(), listener = RedditPostListener.preview(), PostType.IMAGE)
}
