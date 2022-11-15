package com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments

import com.google.gson.annotations.SerializedName
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

data class CommentResponse(
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("data") var commentResponseData: CommentResponseData? = CommentResponseData(),
    var redditPost: RedditPost? = null
)
