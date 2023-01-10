package com.simplicity.simplicityaclientforreddit.main.screen.posts.single

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class SingleListNavigation(
    private val navigator: NavHostController,
    private val navigationListener: NavigationListener,
    val subReddit: String
) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: SingleListLogic = viewModel()
        val state = logic.state.collectAsStateWithLifecycle()
        val screen = SingleListScreen(navigator, logic, state.value)
        logic.init(SingleListInput(navigationListener = navigationListener, subReddit = subReddit), navigator, navigationListener)
        return screen
    }
}
