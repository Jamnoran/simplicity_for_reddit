package com.simplicity.simplicityaclientforreddit.main.usecases.compose

import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SINGLE_LIST

class NavigationToShowPostsUseCase(val navigator: NavHostController, val subreddit: String) {
    fun execute() {
        if (SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, false)) {
//            navigator.navigate(POSTS_LIST.withArgs(subreddit))
            navigator.navigate(SINGLE_LIST.withArgs(subreddit))
        } else {
            navigator.navigate(SINGLE_LIST.withArgs(subreddit))
        }
    }
}
