package com.simplicity.simplicityaclientforreddit.main.usecases.hidden

import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB

class ShowSubUseCase(val sub: String) {
    fun invoke() {
        val db = RoomDB()
        db.showSub(sub)
    }
}
