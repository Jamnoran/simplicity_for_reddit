package com.simplicity.simplicityaclientforreddit.main.screen.search

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class SearchNavigation(private val navController: NavHostController) {

    @Composable
    fun Launch() {
        val logic: SearchLogic = viewModel()
        val screen = SearchScreen(navController, logic)
        logic.start()
        return screen
    }
}
