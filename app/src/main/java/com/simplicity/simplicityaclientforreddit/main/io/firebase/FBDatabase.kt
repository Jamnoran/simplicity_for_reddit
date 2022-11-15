package com.simplicity.simplicityaclientforreddit.main.io.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.simplicity.simplicityaclientforreddit.main.io.firebase.models.FBUser

class FBDatabase {
    private val FBUserLiveData = MutableLiveData<FBUser>()
    private val errorMessage = MutableLiveData<String>()
    private val TAG = "FBDatabase"
    private lateinit var _database: DatabaseReference
    private var _listener: FBDatabaseListener? = null

    fun user(): LiveData<FBUser> {
        return FBUserLiveData
    }

    fun error(): LiveData<String> {
        return errorMessage
    }

    fun initDatabase(listener: FBDatabaseListener) {
        _database = Firebase.database.reference
        _listener = listener

        Log.i(TAG, "Testing database")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }

    fun writeNewUser(userId: String, name: String, ignoredSubReddits: String) {
        val FBUser = FBUser(name, ignoredSubReddits)

        _database.child("users").child(userId).setValue(FBUser)
    }

    fun updateUser(userId: String, ignoredSubReddits: String?) {
        _database.child("users").child(userId).child("ignored").setValue(ignoredSubReddits)
    }

    fun fetchUser(userId: String) {
        Log.i(TAG, "Fetching user with userId: $userId")
        _database.child("users").child(userId).get().addOnSuccessListener {
            Log.i(TAG, "Got value ${it.value}")
            FBUserLiveData.postValue(it.value as FBUser)
            _listener?.userFetched(it.value as FBUser)
        }.addOnFailureListener {
            Log.e(TAG, "Error getting data", it)
            errorMessage.postValue(it.message)
        }
    }

    companion object {
        const val FIREBASE_DB_URL = "https://console.firebase.google.com/project/simplicity-a-client-for-reddit"
    }
}
