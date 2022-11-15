package com.simplicity.simplicityaclientforreddit.main.models.external.responses.user

import com.google.gson.annotations.SerializedName


data class MwebXpromoRevampV3 (

  @SerializedName("owner"         ) var owner        : String? = null,
  @SerializedName("variant"       ) var variant      : String? = null,
  @SerializedName("experiment_id" ) var experimentId : Int?    = null

)