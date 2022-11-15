package com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts

import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class AddCachedPostUseCase(var subreddit: RedditPost) {
    fun execute() {
        var array = GetCachedPostUseCase().execute()
        array = array.plus(subreddit)
        val newValueToStore = Gson().toJson(array)
        SettingsSP().saveSetting(SettingsSP.KEY_CACHED_POSTS, newValueToStore)
    }
}
