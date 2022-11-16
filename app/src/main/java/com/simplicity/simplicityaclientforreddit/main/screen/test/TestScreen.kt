package com.simplicity.simplicityaclientforreddit.main.screen.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun TestScreen(navController: NavHostController, logic: TestLogic, state: UiState<String>) {
    when (state) {
        is UiState.Loading -> ScreenLoading(state.loadingMessage)
        is UiState.Error -> Error()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, state.data)
    }
}

@Composable
fun Show(navController: NavHostController?, data: String) {
    Column(Modifier.fillMaxWidth().background(Color.Green)) {
        Text(text = data)
        var toggled by remember { mutableStateOf(false) }
        TextButton(
            modifier = Modifier.background(Color.Transparent),
            onClick = { toggled = !toggled }
        ) {
            CImage(
                iconResource = if (toggled) R.drawable.up_arrow_clicked else R.drawable.up_arrow_disabled
            )
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
