package com.simplicity.simplicityaclientforreddit.main.screen.settings.hiddensubs

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class HiddenSubsNavigation(private val navController: NavHostController) {

    @Composable
    fun Launch() {
        val logic: HiddenSubsLogic = viewModel()
        val screen = HiddenSubsScreen(navController, logic)
        logic.start()
        return screen
    }
}
