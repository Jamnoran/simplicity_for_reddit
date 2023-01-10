package com.simplicity.simplicityaclientforreddit.main.screen.posts.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class PostDetailNavigation(val navigator: NavHostController, val navigationListener: NavigationListener) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: PostDetailLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = PostDetailScreen(navigator, logic, state.value)
        logic.init(null, navigator, navigationListener)
        return screen
    }
}
