package com.simplicity.simplicityaclientforreddit.main.screen.splash

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class SplashNavigation(private val navController: NavHostController, val navigationListener: NavigationListener) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: SplashInput? = null) {
        Log.i("SplashNavigatoin", "Navigation called")
        val logic: SplashLogic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = SplashScreen(navController, logic, state.value)
        logic.init(input, navController, navigationListener)
        return screen
    }
}
