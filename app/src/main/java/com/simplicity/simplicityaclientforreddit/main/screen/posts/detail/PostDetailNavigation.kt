package com.simplicity.simplicityaclientforreddit.main.screen.posts.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class PostDetailNavigation(val navigator: NavHostController) {

    @Composable
    fun Launch() {
        val logic: PostDetailLogic = viewModel()
        val screen = PostDetailScreen(navigator, logic)
        logic.start()
        return screen
    }
}
