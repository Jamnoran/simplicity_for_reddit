package com.simplicity.simplicityaclientforreddit.main.screen.posts.list

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class PostsListNavigation(
    val navigator: NavHostController,
    val navigationListener: NavigationListener,
    val subReddit: String
) {
    private val logic: PostsListLogic = PostsListLogic()

    @Composable
    fun Launch() {
        val screen = PostsListScreen(navigator, logic)
        logic.start(navigationListener)
        return screen
    }
}
