package com.simplicity.simplicityaclientforreddit.main.screen.profile

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyProfileLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<String>> = _stateFlow

    fun start() {
        background {
            // Do something in the background
            val call = APIAuthenticated().userMe()
            call.enqueue(object : CustomResponseCompose<User>(this) {
                override fun success(responseBody: User) {
                    Log.i(TAG, "Got comments $responseBody")
                }
            })
            foreground {
                _stateFlow.emit(UiState.Success("Hidden!"))
            }
        }
    }

    companion object {
        const val TAG = "MyProfileLogic"
    }
}
