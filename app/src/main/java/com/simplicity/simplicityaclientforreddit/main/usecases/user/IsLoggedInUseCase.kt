package com.simplicity.simplicityaclientforreddit.main.usecases.user

import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class IsLoggedInUseCase {
    fun execute(): Boolean {
        val accessToken = SettingsSP().loadSetting(SettingsSP.KEY_ACCESS_TOKEN, null)
        return !accessToken.isNullOrBlank()
    }
}
