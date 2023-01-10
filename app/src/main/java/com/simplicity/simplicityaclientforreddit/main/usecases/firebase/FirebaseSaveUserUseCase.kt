package com.simplicity.simplicityaclientforreddit.main.usecases.firebase

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.firebase.models.FBUser
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import com.simplicity.simplicityaclientforreddit.main.usecases.user.GetUsernameUseCase

class FirebaseSaveUserUseCase {
    fun execute() {
        val db = RoomDB()
        val listOfHiddenSubs = db.getAllHiddenSubs()
        val stringList = listOfHiddenSubs.map { it.sub }
        // Use joinToString() to concatenate the strings into a single string, separated by commas
        val hiddenSubsInString = stringList.joinToString(separator = ",")
        val userName = GetUsernameUseCase().execute()
        userName?.let {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users")
            val child = myRef.child(userName)
            val testUser = FBUser(userName, hiddenSubsInString)
            val jsonStringOfUser = Gson().toJson(testUser)

            child.setValue(jsonStringOfUser)
            Log.i("FirebaseSaveUserUseCase", "Saved user with this data $testUser")
        }
    }
}
