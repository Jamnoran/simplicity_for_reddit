package com.simplicity.simplicityaclientforreddit.main.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground
import com.simplicity.simplicityaclientforreddit.main.theme.Tertiary
import com.simplicity.simplicityaclientforreddit.main.usecases.compose.NavigationToShowPostsUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.GetSubRedditVisitedUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.RemoveSubRedditVisitedUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.user.IsLoggedInUseCase

@Composable
fun NavigationDrawer(navigator: NavHostController, close: () -> Unit) {
    Column(
        Modifier
            .background(Background)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Profile(navigator, close)
        VisitedSubs(navigator, close)
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationButtons(navigator, close)
    }
}

@Composable
fun Profile(navigator: NavHostController, close: () -> Unit) {
    val loggedIn = IsLoggedInUseCase().execute()
    if (loggedIn) {
        CText(
            modifier = Modifier.clickable {
                close.invoke()
                navigator.navigate(NavRoute.MY_PROFILE.path)
            },
            text = "You are logged in"
        )
    } else {
        CText(
            modifier = Modifier.clickable {
                close.invoke()
                navigator.navigate(NavRoute.AUTHENTICATION.path)
            },
            text = "Log in by clicking here"
        )
    }
    Spacer(Modifier.height(20.dp))
    Divider()
}

@Composable
fun VisitedSubs(navigator: NavHostController, close: () -> Unit) {
    var visitedSubs by remember { mutableStateOf(GetSubRedditVisitedUseCase().execute()) }
    LazyColumn() {
        items(visitedSubs) {
            SubRedditMenuItem(it, navigator, close) {
                visitedSubs = GetSubRedditVisitedUseCase().execute()
            }
        }
    }
}

@Composable
fun SubRedditMenuItem(subreddit: String, navigator: NavHostController, close: () -> Unit, subredditListUpdate: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CText(
                modifier = Modifier
                    .clickable { NavigationToShowPostsUseCase(navigator, subreddit).execute() }
                    .padding(top = 16.dp, bottom = 16.dp),
                text = "r/$subreddit",
                color = OnBackground
            )
            Spacer(
                modifier = Modifier
                    .clickable {
                        close.invoke()
                        NavigationToShowPostsUseCase(navigator, subreddit).execute()
                    }
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
fun BottomNavigationButtons(navigator: NavHostController, close: () -> Unit) {
    Row() {
        CText(
            Modifier.clickable {
                close.invoke()
                navigator.navigate(NavRoute.SETTINGS.path)
            },
            text = "Settings",
            color = OnBackground
        )
        Spacer(Modifier.width(16.dp))
        CText(
            Modifier.clickable {
                close.invoke()
                navigator.navigate(NavRoute.SEARCH.path)
            },
            text = "Search",
            color = OnBackground
        )
    }
}

@Preview
@Composable
fun PreviewNavigationDrawer() {
    Column() {
        NavigationDrawer(navigator = rememberNavController()) {}
    }
}

@Preview
@Composable
fun PreviewNavigationDrawerItems() {
    Column() {
        SubRedditMenuItem(subreddit = "Aww", navigator = rememberNavController(), {}) {}
    }
}
