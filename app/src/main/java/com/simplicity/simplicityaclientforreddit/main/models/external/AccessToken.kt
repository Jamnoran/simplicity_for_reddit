package com.simplicity.simplicityaclientforreddit.main.models.external

import com.google.gson.annotations.SerializedName


data class AccessToken(
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("token_type") var tokenType: String? = null,
    @SerializedName("expires_in") var expiresIn: Int? = null,
    @SerializedName("refresh_token") var refreshToken: String? = null,
    @SerializedName("scope") var scope: String? = null

)