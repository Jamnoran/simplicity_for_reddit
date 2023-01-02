package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase

@Composable
fun PostBody(post: RedditPost, listener: RedditPostListener, type: PostType) {
    Column {
        when (type) {
            LINK -> {
                PostLink(post = post, listener = listener)
            }
            GALLERY -> {
                PostGallery(post, listener)
            }
            IMAGE -> {
                PostImage(post, listener)
            }
            IS_VIDEO -> {
                PostVideo(post = post, listener = listener)
            }
            RICH_VIDEO -> {
                PostVideo(post = post, listener = listener)
            }
            TOURNAMENT -> {
//                binding.root.visibility = View.GONE
                listener.nextPost.invoke()
            }
            IMGUR_LINK -> {
                PostImgur(post = post, listener = listener)
            }
            YOUTUBE -> {
                PostYoutube(post, listener)
            }
            NONE -> {
                PostNone(post, listener)
            }
            DELETED -> {
                PostDeleted(post, listener)
            }
            REDDIT_REPOST -> {
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
        }
    }
}

@Composable
fun PostDeleted(post: RedditPost, listener: RedditPostListener) {
    CText(modifier = Modifier.padding(50.dp), text = "This post has been deleted", color = OnSurface)
}

@Preview
@Composable
fun PostBodyPreview() {
    PostBody(post = TesterHelper.getPost(), listener = RedditPostListener.preview(), IMAGE)
}
