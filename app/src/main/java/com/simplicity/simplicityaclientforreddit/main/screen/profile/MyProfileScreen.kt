package com.simplicity.simplicityaclientforreddit.main.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun MyProfileScreen(navController: NavHostController, logic: MyProfileLogic) {
    logic.stateFlow.collectAsState().value.let { state ->
        when (state) {
            is UiState.Loading -> ScreenLoading(state.loadingMessage)
            is UiState.Error -> Error()
            is UiState.Empty -> {}
            is UiState.Success -> Show(navController, state.data)
        }
    }
}

@Composable
fun Show(navController: NavHostController?, data: String) {
    Column() {
        Button(onClick = { navController?.popBackStack() }) {
            Text("Go back")
        }
        Text(text = data)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(null, "Testing")
    }
}