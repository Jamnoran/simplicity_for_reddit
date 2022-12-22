package com.simplicity.simplicityaclientforreddit.main.screen.posts.fullscreen.image

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class FullScreenImageNavigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: FullScreenImageInput? = null) {
        val logic: FullScreenImageLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = FullScreenImageScreen(navController, logic, state.value)
        logic.init(input)
        return screen
    }
}
