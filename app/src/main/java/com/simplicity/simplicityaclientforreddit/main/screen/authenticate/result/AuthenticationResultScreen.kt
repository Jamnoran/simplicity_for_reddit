package com.simplicity.simplicityaclientforreddit.main.screen.authenticate.result

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun AuthenticationResultScreen(navController: NavHostController, logic: AuthenticationResultLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Error -> ScreenError()
        is UiState.Loading -> ScreenLoading()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, logic, state.data)
    }
}

@Composable
fun Show(navController: NavHostController?, logic: AuthenticationResultLogic, data: Data) {
    DefaultScreen {
        Column {
            CText(text = data.data)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), AuthenticationResultLogic(), Data.preview())
    }
}
