package com.simplicity.simplicityaclientforreddit.main.models.external.responses.search

import com.google.gson.annotations.SerializedName

data class SearchRedditResponse(
    @SerializedName("names") var names: ArrayList<String>
)
