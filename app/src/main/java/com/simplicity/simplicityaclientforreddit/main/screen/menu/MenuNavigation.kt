package com.simplicity.simplicityaclientforreddit.main.screen.menu

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class MenuNavigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: MenuInput? = null) {
        val logic: MenuLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = MenuScreen(navController, logic, state.value)
        logic.init(input)
        return screen
    }
}
