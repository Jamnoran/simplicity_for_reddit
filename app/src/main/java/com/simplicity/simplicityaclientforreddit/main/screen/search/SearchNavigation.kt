package com.simplicity.simplicityaclientforreddit.main.screen.search

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class SearchNavigation(private val navigator: NavHostController, val navigationListener: NavigationListener) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: SearchLogic = viewModel()
        val state = logic.state.collectAsStateWithLifecycle()
        val screen = SearchScreen(navigator, logic, state.value)
        logic.init(null, navController = navigator, navigationListener = navigationListener)
        return screen
    }
}
