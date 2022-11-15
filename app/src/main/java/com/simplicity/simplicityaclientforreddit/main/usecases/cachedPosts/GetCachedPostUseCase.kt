package com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts

import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class GetCachedPostUseCase {
    fun execute(): List<RedditPost> {
        val storedValue = SettingsSP().loadSetting(SettingsSP.KEY_CACHED_POSTS, "")
        return if (storedValue?.isNotEmpty() == true) {
            return Gson().fromJson(storedValue, Array<RedditPost>::class.java).toList()
        } else {
            emptyList()
        }
    }
}
