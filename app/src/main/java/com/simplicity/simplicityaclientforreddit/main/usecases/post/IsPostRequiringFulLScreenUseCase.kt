package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType

class IsPostRequiringFulLScreenUseCase(val redditPost: RedditPost?) {
    fun execute(): Boolean {
        if (redditPost == null) return false
        redditPost.data.let { data ->
            if (GetPostTypeUseCase().execute(data) == PostType.LINK) {
                return true
            }
        }
        return false
    }
}
