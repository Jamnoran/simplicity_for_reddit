package com.simplicity.simplicityaclientforreddit.main.screen.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class TestNavigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: TestLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = TestScreen(navController, logic, state.value)
        LaunchedEffect(Unit) {
            logic.start()
        }
        return screen
    }
}
