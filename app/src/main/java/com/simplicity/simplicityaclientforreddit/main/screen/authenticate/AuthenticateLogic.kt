package com.simplicity.simplicityaclientforreddit.main.screen.authenticate

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthenticateLogic : BaseComposeLogic<AuthenticateInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Success(Data("")))
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready(input: AuthenticateInput) {
        startSignIn(input.navigationListener)
    }

    private fun startSignIn(navigationListener: NavigationListener) {
        Log.i("AuthenticateLogic", "Starting signing with url $AUTH_URL")
        val url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        navigationListener.navigate(intent)
    }

    companion object {
        const val AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
            "&response_type=code&state=%s&redirect_uri=%s&" +
            "duration=permanent&scope=identity,edit,flair,history,mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,creddits"
        const val CLIENT_ID = "5IyfyqHZKHflfxTAKUj3zg"
        const val REDIRECT_URI = "simplicity://com.simplicity/redirect"
        const val STATE = "MY_RANDOM_STRING_1"
    }
}
