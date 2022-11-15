package com.simplicity.simplicityaclientforreddit.main.screen.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.screens.Loading
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun TestScreen(navController: NavHostController, logic: TestLogic, state: UiState<String>) {
    when (state) {
        is UiState.Loading -> Loading(state.loadingMessage)
        is UiState.Error -> Error()
        is UiState.Success -> Show(navController, state.data)
    }
}

@Composable
fun Show(navController: NavHostController?, data: String) {
    Column(Modifier.fillMaxWidth().background(Color.Green)) {
        Text(text = data)
        CImage(Modifier.fillMaxWidth(), url = "https://media.istockphoto.com/id/1398788867/photo/decorative-abstract-fragility-blue-shade-wavy-background.jpg?b=1&s=170667a&w=0&k=20&c=M_8ioW-qyip4-DwB041vBB8KOsKFhMTudJjcEDfjmhc=")
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
