package com.simplicity.simplicityaclientforreddit.main.usecases.authentication

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class GetAccessTokenBodyUseCase {

    fun getBody(code: String): RequestBody {
        return RequestBody.create(
            "application/x-www-form-urlencoded".toMediaTypeOrNull(),
            "grant_type=authorization_code&code=" + code +
                "&redirect_uri=" + REDIRECT_URI
        )
    }

    companion object {
        const val REDIRECT_URI = "simplicity://com.simplicity/redirect"
    }
}
