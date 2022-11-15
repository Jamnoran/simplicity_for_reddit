package com.simplicity.simplicityaclientforreddit.main.models.external.responses

import com.google.gson.annotations.SerializedName
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class FetchPostsResponse(
    @SerializedName("data")
    val data: Data
) {
    class Data(
        @SerializedName("after")
        val after: String?,
        @SerializedName("dist")
        val dist: Int,
        @SerializedName("children")
        val children: List<RedditPost>?
    )
}
