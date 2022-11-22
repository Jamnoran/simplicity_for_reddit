package com.simplicity.simplicityaclientforreddit.main.screen.user

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import com.simplicity.simplicityaclientforreddit.main.theme.BodyLarge
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase

@Composable
fun UserScreen(navController: NavHostController, navigationListener: NavigationListener, logic: UserLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Error -> ScreenError()
        is UiState.Loading -> ScreenLoading()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, navigationListener, logic, state.data)
    }
}

@Composable
fun Show(navController: NavHostController?, navigationListener: NavigationListener, logic: UserLogic, data: Data) {
    DefaultScreen {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            data.user?.let { user ->
                Box(Modifier.fillMaxWidth()) {
                    user.bannerImg?.let {
                        CImage(Modifier.fillMaxWidth(), it, ContentScale.FillWidth)
                    }
                    Column {
                        user.name?.let { CText(text = it, style = BodyLarge) }
                        user.created?.let { CText(text = GetTimeAgoUseCase().execute(it)) }
                        user.totalKarma?.let { CText(text = "Karma: $it") }
                    }
                }
            }
            // Posts
            if (data.posts.isNotEmpty()) {
                Column(Modifier.fillMaxWidth()) {
                    for (post in data.posts) {
                        CText(
                            Modifier.clickable {
                                val convertedUrl = "https://www.reddit.com${post.data.permalink}"
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
                                navigationListener.navigate.invoke(browserIntent)
                            },
                            text = "Post [${post.data.title}]"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), NavigationListener.preview(), UserLogic(), Data.preview())
    }
}
