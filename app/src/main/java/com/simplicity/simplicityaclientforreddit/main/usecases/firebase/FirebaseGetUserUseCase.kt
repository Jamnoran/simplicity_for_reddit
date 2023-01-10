package com.simplicity.simplicityaclientforreddit.main.usecases.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.firebase.models.FBUser
import com.simplicity.simplicityaclientforreddit.main.usecases.user.GetUsernameUseCase

class FirebaseGetUserUseCase {

    fun execute(valueUpdated: (userValue: FBUser?) -> Unit, onCancelled: (exception: String) -> Unit) {
        val userName = GetUsernameUseCase().execute()
        userName?.let {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users")
            val child = myRef.child(userName)

            child.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue(String::class.java)
                    Log.d("FirebaseGetUserUseCase", "Value is: $value")
                    val user = Gson().fromJson(value, FBUser::class.java)
                    // Update hiddenSubs list in database

                    valueUpdated.invoke(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("FirebaseGetUserUseCase", "Failed to read value.", error.toException())
                    onCancelled.invoke(error.toException().toString())
                }
            })
        }
    }
}
