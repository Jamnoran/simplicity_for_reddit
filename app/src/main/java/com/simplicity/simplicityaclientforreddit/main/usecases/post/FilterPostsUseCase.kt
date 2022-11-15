package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class FilterPostsUseCase {
    fun canDisplay(redditPost: RedditPost, nsfwSettings: Boolean, sfwSettings: Boolean): Boolean {
        val db = RoomDB()
        val converted = db.toReadPost(redditPost)
        return if (converted != null) {
            if (redditPost.data.subreddit.let { db.isSubHidden(it) }) {
                return false
            }
            if (!nsfwCheckCanShow(redditPost, nsfwSettings, sfwSettings)) {
                return false
            }
            val hasRead = db.getReadPost(converted)
            if (hasRead == null && SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_CACHE, true)) {
                db.setReadPost(converted)
            }
            hasRead == null
        } else {
            false
        }
    }

    private fun nsfwCheckCanShow(redditPost: RedditPost, nsfwSettings: Boolean, sfwSettings: Boolean): Boolean {
        if (redditPost.data.over_18 == true && nsfwSettings) {
            return true
        }
        if (redditPost.data.over_18 == false && sfwSettings) {
            return true
        }
        return false
    }
}
