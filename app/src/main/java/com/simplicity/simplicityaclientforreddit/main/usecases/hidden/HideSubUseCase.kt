package com.simplicity.simplicityaclientforreddit.main.usecases.hidden

import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB

class HideSubUseCase(val sub: String) {
    fun invoke() {
        val db = RoomDB()
        if (!db.isSubHidden(sub)) {
            db.hideSub(sub)
        }
    }
}
