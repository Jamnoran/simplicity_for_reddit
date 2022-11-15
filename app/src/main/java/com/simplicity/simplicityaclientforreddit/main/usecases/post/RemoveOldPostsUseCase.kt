package com.simplicity.simplicityaclientforreddit.main.usecases.post

import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB

class RemoveOldPostsUseCase {
    fun removeOld() {
        val db = RoomDB()
        db.deleteAllOlderThanAWeek()
    }
}
