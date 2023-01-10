package com.simplicity.simplicityaclientforreddit.main.screen.splash

import com.google.firebase.auth.FirebaseAuth
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.usecases.compose.NavigationToShowPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashLogic : BaseComposeLogic<SplashInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready() {
        loggI("Starting application")
        background {
            signIn()
            // Do something in the background
            foreground {
                navigate()?.let {
                    NavigationToShowPostsUseCase(it, "").execute()
//                    it.navigate(NavRoute.TEST.path)
//                    it.navigate(NavRoute.POST_DETAIL.path)
                }
            }
        }
    }

    private fun signIn() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    loggI("User isAnonymous: ${user.isAnonymous}")
                }
            } else {
                // If sign in fails, display a message to the user.
                loggW("signInAnonymously:failure", task.exception)
            }
        }
    }
}
