package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class DownloadIfRequiredLinkedRedditPostUseCase(val redditPost: RedditPost) {
    fun execute() {
        if(redditPost.data.postHint != "link"){
            return
        }
    }
}
