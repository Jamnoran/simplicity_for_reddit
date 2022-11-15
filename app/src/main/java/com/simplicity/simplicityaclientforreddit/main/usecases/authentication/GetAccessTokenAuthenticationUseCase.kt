package com.simplicity.simplicityaclientforreddit.main.usecases.authentication

import android.util.Base64

class GetAccessTokenAuthenticationUseCase {
    fun getAuth(): String {
        val authString = "$CLIENT_ID:"
        val encodedAuthString: String = Base64.encodeToString(
            authString.toByteArray(),
            Base64.NO_WRAP
        )

        return "Basic $encodedAuthString"
    }

    companion object {
        const val CLIENT_ID = "5IyfyqHZKHflfxTAKUj3zg"
    }
}
