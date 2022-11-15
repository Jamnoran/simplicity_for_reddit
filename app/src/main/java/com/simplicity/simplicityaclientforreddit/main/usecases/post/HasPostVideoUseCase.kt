package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType

class HasPostVideoUseCase {
    fun execute(data: RedditPost.Data): Boolean {
        return when (GetPostTypeUseCase().execute(data)) {
            PostType.LINK,
            PostType.GALLERY,
            PostType.IMAGE,
            PostType.TOURNAMENT,
            PostType.IMGUR_LINK,
            PostType.NONE,
            PostType.DELETED,
            PostType.YOUTUBE -> false
            PostType.IS_VIDEO,
            PostType.RICH_VIDEO -> true
        }
    }
}
