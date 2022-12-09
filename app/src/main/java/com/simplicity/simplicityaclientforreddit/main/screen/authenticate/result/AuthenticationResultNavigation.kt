package com.simplicity.simplicityaclientforreddit.main.screen.authenticate.result

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class AuthenticationResultNavigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: AuthenticationResultLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = AuthenticationResultScreen(navController, logic, state.value)
        logic.init(AuthenticationResultInput(navController))
        return screen
    }
}
