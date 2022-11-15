package com.simplicity.simplicityaclientforreddit.main.models.internal

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

data class Session(
    var id: String,
    var firstId: String?,
    var lastReadId: String?,
    var nextId: String?,
    var count: Int = 0,
    var unreadPosts: List<RedditPost>?
)
