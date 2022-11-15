package com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts

import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class RemoveCachedPostUseCase(var post: RedditPost) {
    fun execute() {
        val array = GetCachedPostUseCase().execute()
        val mutableList = array.toMutableList()
        mutableList.remove(post)
        val newValueToStore = Gson().toJson(mutableList)
        SettingsSP().saveSetting(SettingsSP.KEY_CACHED_POSTS, newValueToStore)
    }
}
