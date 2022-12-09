package com.simplicity.simplicityaclientforreddit.main.screen.comments2

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class Comments2Navigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: Comments2Input? = null) {
        val logic: Comments2Logic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = Comments2Screen(navController, logic, state.value)
        logic.init(input)
        return screen
    }
}
