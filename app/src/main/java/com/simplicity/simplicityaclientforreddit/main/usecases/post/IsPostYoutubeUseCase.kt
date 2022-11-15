package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class IsPostYoutubeUseCase {
    fun execute(data: RedditPost.Data): Boolean {
        if (data.urlOverriddenByDest?.startsWith("https://www.youtube.com/watch?v=") == true) {
            return true
        } else if (data.urlOverriddenByDest?.startsWith("https://youtu.be/") == true) {
            return true
        }
        return false
    }
}
