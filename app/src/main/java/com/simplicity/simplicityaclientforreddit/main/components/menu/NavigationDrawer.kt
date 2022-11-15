package com.simplicity.simplicityaclientforreddit.main.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.OnPrimary
import com.simplicity.simplicityaclientforreddit.main.theme.Tertiary
import com.simplicity.simplicityaclientforreddit.main.usecases.compose.NavigationToShowPostsUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.GetSubRedditVisitedUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.RemoveSubRedditVisitedUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.user.IsLoggedInUseCase

@Composable
fun NavigationDrawer(navigator: NavHostController) {
    Column(
        Modifier
            .background(Background)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Profile(navigator)
        VisitedSubs(navigator)
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationButtons(navigator)
    }
}

@Composable
fun Profile(navigator: NavHostController) {
    val loggedIn = IsLoggedInUseCase().execute()
    if (loggedIn) {
        CText(modifier = Modifier.clickable { navigator.navigate(NavRoute.MY_PROFILE.path) }, text = "You are logged in")
    } else {
        CText(text = "Log in by clicking here")
    }
    Spacer(Modifier.height(20.dp))
    Divider()
}

@Composable
fun VisitedSubs(navigator: NavHostController) {
    var visitedSubs by remember { mutableStateOf(GetSubRedditVisitedUseCase().execute()) }
    LazyColumn() {
        items(visitedSubs) {
            SubRedditMenuItem(it, navigator) {
                visitedSubs = GetSubRedditVisitedUseCase().execute()
            }
        }
    }
}

@Composable
fun SubRedditMenuItem(subreddit: String, navigator: NavHostController, subredditListUpdate: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CText(
                modifier = Modifier
                    .clickable { NavigationToShowPostsUseCase(navigator, subreddit).execute() }
                    .padding(top = 16.dp, bottom = 16.dp),
                text = "r/$subreddit",
                color = OnPrimary
            )
            Spacer(
                modifier = Modifier
                    .clickable { NavigationToShowPostsUseCase(navigator, subreddit).execute() }
                    .padding(top = 16.dp, bottom = 16.dp)
                    .weight(1f)
            )
            Icon(
                modifier = Modifier.clickable {
                    // Remove subreddit
                    RemoveSubRedditVisitedUseCase(subreddit).execute()
                    subredditListUpdate.invoke()
                },
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Remove sub",
                tint = Tertiary
            )
        }
        Divider()
    }
}

@Composable
fun BottomNavigationButtons(navigator: NavHostController) {
    Row() {
        CText(Modifier.clickable { navigator.navigate(NavRoute.SETTINGS.path) }, text = "Settings", color = OnPrimary)
        Spacer(Modifier.width(16.dp))
        CText(Modifier.clickable { navigator.navigate(NavRoute.SEARCH.path) }, text = "Search", color = OnPrimary)
    }
}

@Preview
@Composable
fun PreviewNavigationDrawer() {
    Column() {
        NavigationDrawer(navigator = rememberNavController())
    }
}

@Preview
@Composable
fun PreviewNavigationDrawerItems() {
    Column() {
        SubRedditMenuItem(subreddit = "Aww", navigator = rememberNavController()) {}
    }
}
