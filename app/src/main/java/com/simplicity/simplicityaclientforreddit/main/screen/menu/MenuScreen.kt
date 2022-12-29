package com.simplicity.simplicityaclientforreddit.main.screen.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.menu.NavigationDrawer
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.usecases.user.IsLoggedInUseCase

@Composable
fun MenuScreen(navController: NavHostController, logic: MenuLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Error -> ScreenError()
        is UiState.Loading -> ScreenLoading()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, logic, state.data)
    }
}

@Composable
fun Show(navigator: NavHostController, logic: MenuLogic, data: Data) {
    DefaultScreen {
        Column {
            NavigationDrawer(navigator = navigator, isLoggedIn = IsLoggedInUseCase().execute(), closeDrawer = {
//                navigator.popBackStack()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), MenuLogic(), Data.preview())
    }
}
