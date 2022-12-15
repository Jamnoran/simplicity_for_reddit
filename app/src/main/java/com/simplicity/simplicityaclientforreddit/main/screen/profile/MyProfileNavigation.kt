package com.simplicity.simplicityaclientforreddit.main.screen.profile

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class MyProfileNavigation(private val navigator: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: MyProfileLogic = viewModel()
        val state = logic.state.collectAsStateWithLifecycle()
        val screen = MyProfileScreen(navigator, logic, state.value)
        logic.start()
        return screen
    }
}
