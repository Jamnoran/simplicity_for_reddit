package com.simplicity.simplicityaclientforreddit.main.screen.test

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class TestNavigation(
    private val navController: NavHostController,
    val navigationListener: NavigationListener
) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: TestLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = TestScreen(navController, logic, state.value)
        logic.init(null, navController, navigationListener)
        return screen
    }
}
