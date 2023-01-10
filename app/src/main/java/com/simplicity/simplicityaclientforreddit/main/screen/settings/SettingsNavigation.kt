package com.simplicity.simplicityaclientforreddit.main.screen.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class SettingsNavigation(
    private val navController: NavHostController,
    private val navigationListener: NavigationListener
) {
    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: SettingsInput? = SettingsInput()) {
        val logic: SettingsLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = SettingsScreen(navController, logic, state.value)
        logic.init(input = input, navController = navController, navigationListener = navigationListener)
        return screen
    }
}
