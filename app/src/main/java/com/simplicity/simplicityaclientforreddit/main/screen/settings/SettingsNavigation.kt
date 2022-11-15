package com.simplicity.simplicityaclientforreddit.main.screen.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class SettingsNavigation(private val navController: NavHostController) {

    @Composable
    fun Launch() {
        val logic: SettingsLogic = viewModel()
        val screen = SettingsScreen(navController, logic)
        logic.start()
        return screen
    }
}
