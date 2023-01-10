package com.simplicity.simplicityaclientforreddit.main.usecases.compose

import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SINGLE_LIST
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.AddSubRedditVisitedUseCase

class NavigationToShowPostsUseCase(val navigator: NavHostController, val subreddit: String? = null) {
    fun execute() {
        if (subreddit != null && subreddit.isNotEmpty()) {
            AddSubRedditVisitedUseCase(subreddit = subreddit).execute()
            if (SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, false)) {
//            navigator.navigate(POSTS_LIST.withArgs(subreddit))
                navigator.navigate(SINGLE_LIST.withArgs(subreddit))
            } else {
                navigator.navigate(SINGLE_LIST.withArgs(subreddit))
            }
        } else {
            if (SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, false)) {
//            navigator.navigate(POSTS_LIST.withArgs(subreddit))
                navigator.navigate(SINGLE_LIST.withArgs(null))
            } else {
                navigator.navigate(SINGLE_LIST.withArgs(null))
            }
        }
    }
}
