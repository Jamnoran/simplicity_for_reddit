package com.simplicity.simplicityaclientforreddit.main.usecases

import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class IsFirstTimeApplicationStartedUseCase {
    fun isFirstTime(): Boolean {
        val firstTime = SettingsSP().loadSetting("FIRST_TIME", true)
        SettingsSP().saveSetting("FIRST_TIME", false)
        return firstTime
    }
}
