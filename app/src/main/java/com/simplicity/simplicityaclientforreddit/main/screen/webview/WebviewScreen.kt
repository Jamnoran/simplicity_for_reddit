package com.simplicity.simplicityaclientforreddit.main.screen.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.web.CWebView
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun WebViewScreen(navigator: NavHostController, logic: WebViewLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Loading -> ScreenLoading(state.loadingMessage)
        is UiState.Error -> ScreenError()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navigator, state.data)
    }
}

@Composable
fun Show(navigator: NavHostController?, data: Data) {
    CWebView(url = data.url)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(null, Data("Testing"))
    }
}
