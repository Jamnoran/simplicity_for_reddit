package com.simplicity.simplicityaclientforreddit.main.screen.profile

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class MyProfileNavigation(private val navController: NavHostController) {

    @Composable
    fun Launch() {
        val logic: MyProfileLogic = viewModel()
        return MyProfileScreen(navController, logic)
    }
}
