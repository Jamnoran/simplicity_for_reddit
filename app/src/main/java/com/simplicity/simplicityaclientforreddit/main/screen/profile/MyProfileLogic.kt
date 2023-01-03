package com.simplicity.simplicityaclientforreddit.main.screen.profile

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyProfileLogic : BaseLogic() {
    private val _state = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val state: StateFlow<UiState<Data>> = _state

    fun start() {
        // Execute the following code in a background thread
        background {
            // Create a new API call to get the user
            val call = APIAuthenticated().userMe()
            // Enqueue the API call and provide a callback object to handle the response
            call.enqueue(object : CustomResponseCompose<User>(this) {
                // Called when the API call was successful and the server returned a response
                override fun success(responseBody: User) {
                    // Log a message indicating that the user was obtained
                    Log.i(TAG, "Got user $responseBody")
                    // Execute the following code in the main (foreground) thread
                    foreground {
                        // Update the state of the UI to show that the request was successful
                        // and provide the data (the name of the user) to be displayed
                        _state.emit(UiState.Success(Data("User: ${responseBody.name}")))
                    }
                }

                // Called when the API call failed and the server did not return a response
                override fun failed(reason: String) {
                    // Log an error message indicating that the user could not be obtained
                    Log.e(TAG, "Could not get user")
                }
            })
        }
    }

    companion object {
        const val TAG = "MyProfileLogic"
    }
}
