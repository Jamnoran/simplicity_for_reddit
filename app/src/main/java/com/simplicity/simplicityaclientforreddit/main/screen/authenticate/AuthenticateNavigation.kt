package com.simplicity.simplicityaclientforreddit.main.screen.authenticate

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class AuthenticateNavigation(private val navController: NavHostController, private val navigationListener: NavigationListener) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: AuthenticateLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = AuthenticateScreen(navController, logic, state.value)
        logic.init(input = AuthenticateInput(navigationListener), navController = navController, navigationListener = navigationListener)
        return screen
    }
}
