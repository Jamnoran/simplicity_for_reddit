package com.simplicity.simplicityaclientforreddit.main.io.settings

import android.content.SharedPreferences
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.Global

class SettingsSP {
    fun saveSetting(key: String, value: Boolean) {
        val editor = getPreferences()?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun saveSetting(key: String, value: String?) {
        val editor = getPreferences()?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun saveSetting(key: String, value: Int) {
        val editor = getPreferences()?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

    fun loadSetting(key: String, defaultValue: Boolean): Boolean {
        val preferences = getPreferences()
        return preferences?.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun loadSetting(key: String, defaultValue: String?): String? {
        val preferences = getPreferences()
        return preferences?.getString(key, defaultValue) ?: defaultValue
    }

    fun loadSetting(key: String, defaultValue: Int): Int {
        val preferences = getPreferences()
        return preferences?.getInt(key, defaultValue) ?: defaultValue
    }

    private fun getPreferences(): SharedPreferences? {
        return try {
            com.simplicity.simplicityaclientforreddit.main.Global.applicationContext
                .getSharedPreferences("RedditPreferences", 0) // 0 - for private mode
        } catch (e: Exception) {
            Log.e("SettingsSP", "Could not get shared preferences, find a new way to call for it", e)
            null
        }
    }

    companion object {
        const val KEY_USER = "USER"
        const val KEY_DEVICE_HEIGHT = "DEVICE_HEIGHT"
        const val KEY_DEVICE_WIDTH = "DEVICE_WIDTH"
        const val KEY_MUTE_VIDEOS = "MUTE_VIDEOS"
        const val KEY_PREVIOUS_VISITED_SUBREDDITS = "KEY_PREVIOUS_VISITED_SUBREDDITS"
        const val KEY_CACHED_POSTS = "KEY_CACHED_POSTS"
        const val KEY_CODE = "CODE"
        const val KEY_STATE = "STATE"
        const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"
        const val KEY_SETTINGS_SCROLL_BOTTOM = "SETTINGS_SCROLL_BOTTOM"
        const val KEY_SETTINGS_SCROLL_VOLUME = "SETTINGS_SCROLL_VOLUME"
        const val KEY_SETTINGS_USE_CACHE = "SETTINGS_USE_CACHE"
        const val KEY_SETTINGS_USE_LIST = "SETTINGS_USE_LIST"
        const val KEY_SETTINGS_SHOW_LINK_IN_WEB_VIEW_UNDER_POST = "SETTINGS_SHOW_LINK_IN_WEB_VIEW_UNDER_POST"
        const val KEY_SHOW_LINKS_UNDER_POST = "SHOW_LINKS_UNDER_POST"
        const val KEY_SETTINGS_NSFW = "NSFW_key"
        const val KEY_SETTINGS_SFW = "SFW_key"
        const val KEY_ID = "id_key"
        const val KEY_SUB_REDDIT = "sub_reddit_key"
        const val KEY_AUTHOR = "sub_author"
    }
}
