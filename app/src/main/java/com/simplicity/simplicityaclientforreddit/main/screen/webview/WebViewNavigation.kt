package com.simplicity.simplicityaclientforreddit.main.screen.webview

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class WebViewNavigation(private val navController: NavHostController, val url: String) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch() {
        val logic: WebViewLogic = viewModel()
        val state = logic.state.collectAsStateWithLifecycle()
        val screen = WebViewScreen(navController, logic, state.value)
        logic.init(url)
        return screen
    }
}
