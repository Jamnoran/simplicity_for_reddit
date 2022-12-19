package com.simplicity.simplicityaclientforreddit.main.screen.comments

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class CommentsNavigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: CommentsInput? = null) {
        val logic: CommentsLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = CommentsScreen(navController, logic, state.value)
        logic.init(input)
        return screen
    }
}
