package com.simplicity.simplicityaclientforreddit.main.listeners

import com.simplicity.simplicityaclientforreddit.databinding.MediaVideoPlayerBinding
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

interface OldRedditPostListener {
    fun voteUp(post: RedditPost)
    fun voteDown(post: RedditPost)
    fun linkClicked(post: RedditPost)
    fun commentsClicked(post: RedditPost)
    fun redditLinkClicked(post: RedditPost)
    fun subRedditClicked(post: RedditPost)
    fun authorClicked(post: RedditPost)
    fun hideSubClicked(post: RedditPost)
    fun directLinkClicked(link: String)
    fun directAuthorClicked(author: String)
    fun directRedditClicked(reddit: String)
    fun sharePost(reddit: RedditPost)
    fun initVideoPlayer(reddit: RedditPost, mediaVideoPlayerBinding: MediaVideoPlayerBinding)
}
