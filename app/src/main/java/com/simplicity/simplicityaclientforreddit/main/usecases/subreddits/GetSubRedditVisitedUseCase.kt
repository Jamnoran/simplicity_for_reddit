package com.simplicity.simplicityaclientforreddit.main.usecases.subreddits

import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class GetSubRedditVisitedUseCase {
    fun execute(): List<String> {
        val storedValue = SettingsSP().loadSetting(SettingsSP.KEY_PREVIOUS_VISITED_SUBREDDITS, "")
        return if (storedValue?.isNotEmpty() == true) {
            storedValue.split(",").map { it.trim() }
        } else {
            emptyList()
        }
    }
}
