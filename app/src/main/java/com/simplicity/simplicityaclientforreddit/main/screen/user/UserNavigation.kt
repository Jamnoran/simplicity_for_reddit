package com.simplicity.simplicityaclientforreddit.main.screen.user

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class UserNavigation(
    private val navController: NavHostController,
    private val navigationListener: NavigationListener,
    private val userName: String
) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: UserLogic = viewModel()
        val state = logic.state.collectAsStateWithLifecycle()
        Log.i("UserNavigation", "Getting updated state with posts: ${state.value}")
        val screen = UserScreen(navController, navigationListener, logic, state.value)
        logic.init(UserInput(userName, navigationListener))
        return screen
    }
}
