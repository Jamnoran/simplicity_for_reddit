package com.simplicity.simplicityaclientforreddit.main.screen.posts.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class PostDetailNavigation(val navigator: NavHostController, val navigationListener: NavigationListener) {

    @Composable
    fun Launch() {
        val logic: PostDetailLogic = viewModel()
        val screen = PostDetailScreen(navigator, logic)
        logic.init(null, navigator, navigationListener)
        return screen
    }
}
