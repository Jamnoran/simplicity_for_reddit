package com.simplicity.simplicityaclientforreddit.main.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun MyProfileScreen(navigator: NavHostController, logic: MyProfileLogic, uiState: UiState<Data>) {
    when (uiState) {
        is UiState.Loading -> ScreenLoading(uiState.loadingMessage)
        is UiState.Error -> ScreenError()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navigator, uiState.data)
    }
}

@Composable
fun Show(navController: NavHostController?, data: Data) {
    Column() {
        Button(onClick = { navController?.popBackStack() }) {
            Text("Go back")
        }
        Text(text = data.username)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(null, Data("Testing"))
    }
}
