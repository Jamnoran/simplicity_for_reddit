package com.simplicity.simplicityaclientforreddit.main.usecases.subreddits

import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class AddSubRedditVisitedUseCase(var subreddit: String) {
    fun execute() {
        var array = GetSubRedditVisitedUseCase().execute()
        if (!array.contains(subreddit)) {
            array = array.plus(subreddit)
            val newValueToStore = array.joinToString(",")
            SettingsSP().saveSetting(SettingsSP.KEY_PREVIOUS_VISITED_SUBREDDITS, newValueToStore)
        }
    }
}
