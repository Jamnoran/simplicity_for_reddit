package com.simplicity.simplicityaclientforreddit.main.usecases.authentication

import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class GetRefreshTokenBodyUseCase {
    fun getBody(): RequestBody {
        return RequestBody.create(
            "application/x-www-form-urlencoded".toMediaTypeOrNull(),
            "grant_type=refresh_token&refresh_token=" + SettingsSP().loadSetting(SettingsSP.KEY_REFRESH_TOKEN, "NOT_AVAILABLE")
        )
    }
}
