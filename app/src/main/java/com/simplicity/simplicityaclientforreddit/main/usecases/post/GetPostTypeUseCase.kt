package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType

class GetPostTypeUseCase {
    fun execute(data: RedditPost.Data): PostType {
        if (data.selftext?.contains("[deleted]") == true) {
            return PostType.DELETED
        }
        if (IsPostYoutubeUseCase().execute(data)) {
            return PostType.YOUTUBE
        }

        if (data.is_video) {
//            Log.i("GetPostTypeUseCase", "PostType.IS_VIDEO")
            return PostType.IS_VIDEO
        }

        if (data.is_gallery) {
//            Log.i("GetPostTypeUseCase", "PostType.GALLERY")
            return PostType.GALLERY
        }
        if (data.tournament_data != null) {
//            Log.i("GetPostTypeUseCase", "PostType.TOURNAMENT")
            return PostType.TOURNAMENT
        }

        // Specific postHint types
        return when (data.postHint) {
            "link" -> {
//                Log.i("GetPostTypeUseCase", "PostType.LINK")
                if (containsTwitch(data)) {
                    return PostType.TWITCH_LINK
                }
                if (containsImgur(data)) {
                    return PostType.IMGUR_LINK
                }
                if (containsReddit(data)) {
                    return PostType.REDDIT_REPOST
                }
                return PostType.LINK
            }
            "rich:video" -> {
//                Log.i("GetPostTypeUseCase", "PostType.RICH_VIDEO")
                PostType.RICH_VIDEO
            }
            "image" -> {
//                Log.i("GetPostTypeUseCase", "PostType.IMAGE")
                PostType.IMAGE
            }
            else -> {
//                Log.i("GetPostTypeUseCase", "PostType.NONE")
                PostType.NONE
            }
        }
    }

    private fun containsTwitch(data: RedditPost.Data): Boolean {
        if (data.url != null && data.url!!.contains("clips.twitch.tv")) {
            return true
        }
        return false
    }

    private fun containsImgur(data: RedditPost.Data): Boolean {
        if (data.url != null && data.url!!.contains("imgur.com")) {
            return true
        }
        return false
    }

    private fun containsReddit(data: RedditPost.Data): Boolean {
        data.crosspostParentList?.let { list ->
            if (list.isNotEmpty()) {
                return true
            }
        }
        return false
    }
}
