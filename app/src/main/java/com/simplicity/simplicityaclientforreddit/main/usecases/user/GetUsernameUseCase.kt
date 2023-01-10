package com.simplicity.simplicityaclientforreddit.main.usecases.user

import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User

class GetUsernameUseCase {
    fun execute(): String? {
        val userJson = SettingsSP().loadSetting(SettingsSP.KEY_USER, null)
        val user = Gson().fromJson(userJson, User::class.java)
        return user.name
    }
}
