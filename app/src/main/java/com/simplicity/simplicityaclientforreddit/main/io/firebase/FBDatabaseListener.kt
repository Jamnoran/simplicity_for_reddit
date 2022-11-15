package com.simplicity.simplicityaclientforreddit.main.io.firebase

import com.simplicity.simplicityaclientforreddit.main.io.firebase.models.FBUser

interface FBDatabaseListener {
    fun userFetched(FBUser: FBUser?) { }
}
