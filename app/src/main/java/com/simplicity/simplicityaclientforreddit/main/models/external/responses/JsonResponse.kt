package com.simplicity.simplicityaclientforreddit.main.models.external.responses

import com.google.gson.annotations.SerializedName

class JsonResponse(
    @SerializedName("code")
    val data: Integer){ }