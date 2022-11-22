package com.simplicity.simplicityaclientforreddit.main.screen.search

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class SearchNavigation(private val navigator: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: SearchLogic = viewModel()
        val state = logic.state.collectAsStateWithLifecycle()
        val screen = SearchScreen(navigator, logic, state.value)
        logic.init(SearchInput("", ""))
        return screen
    }
}
