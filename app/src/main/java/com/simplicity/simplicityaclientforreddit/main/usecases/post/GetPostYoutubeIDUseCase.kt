package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class GetPostYoutubeIDUseCase {
    fun execute(data: RedditPost.Data): String? {
        data.urlOverriddenByDest?.let { url ->
            if (url.startsWith("https://youtu.be/")) {
                return url.substring("https://youtu.be/".length, url.length)
            } else {
                return url.substring("https://www.youtube.com/watch?v=".length, url.length)
            }
        }
        return null
    }
}
