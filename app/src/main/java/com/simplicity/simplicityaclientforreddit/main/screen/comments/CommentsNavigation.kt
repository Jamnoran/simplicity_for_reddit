package com.simplicity.simplicityaclientforreddit.main.screen.comments

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class CommentsNavigation(private val navController: NavHostController, private val postId: String, val subreddit: String) {
    @Composable
    fun Launch() {
        val logic: CommentsLogic = viewModel()
        val screen = CommentsScreen(logic)
        logic.init(postId = postId, subreddit = subreddit)
        return screen
    }
}
