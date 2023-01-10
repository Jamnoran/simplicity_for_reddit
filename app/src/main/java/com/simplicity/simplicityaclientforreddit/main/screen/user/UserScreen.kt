package com.simplicity.simplicityaclientforreddit.main.screen.user

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.Post
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.BodyLarge
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun UserScreen(navigator: NavHostController, logic: UserLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Error -> ScreenError()
        is UiState.Loading -> ScreenLoading()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navigator, logic, state.data, getListener(logic, navigator))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Show(
    navigator: NavHostController?,
    logic: UserLogic,
    data: Data,
    listener: RedditPostListener
) {
    Log.i("UserScreen", "Showing data ${data.posts}")
    // subreddit.icon_img
    // subreddit.public description
    // Ohterwise icon_img
    // banner_img
    var expandeduser by remember { mutableStateOf(true) }
    DefaultScreen {
        Column {
            data.user?.let { user ->
                Box(
                    Modifier.fillMaxWidth().padding(bottom = 16.dp).combinedClickable(
                        onClick = { },
                        onLongClick = { expandeduser = !expandeduser }
                    )
                ) {
                    if (expandeduser) {
                        ExpandedUser(user, logic)
                    } else {
                        CollapsedUser(user, logic)
                    }
                }
                // Posts
                if (data.posts.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(data.posts) { post ->
                            Post(post = post, listener = listener, singlePost = false)
                        }
                    }
                } else {
                    CText("User has no posts")
                }
            }
        }
    }
}

@Composable
fun CollapsedUser(user: User, logic: UserLogic) {
    Column(modifier = Modifier.padding(8.dp)) {
        user.name?.let { CText(modifier = Modifier.clickable { logic.updateScreen() }, text = "u/$it", style = BodyLarge) }
    }
}

@Composable
fun ExpandedUser(user: User, logic: UserLogic) {
    user.bannerImg?.let {
        CImage(Modifier.fillMaxWidth(), it, ContentScale.FillWidth)
    }
    Column(modifier = Modifier.padding(8.dp)) {
        user.name?.let { CText(modifier = Modifier.clickable { logic.updateScreen() }, text = "u/$it", style = BodyLarge) }
        user.created?.let { CText(text = GetTimeAgoUseCase().execute(it)) }
        user.totalKarma?.let { CText(text = "Karma: $it") }
        user.subreddit?.publicDescription?.let { CText(text = "Description: $it") }
    }
}

fun getListener(logic: UserLogic, navigator: NavHostController): RedditPostListener {
    return RedditPostListener(
        downVote = { },
        upVote = { },
        redditClick = { logic.goToReddit(it) },
        authorClick = {},
        shareClick = {},
        readComments = {},
        linkClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())))
        },
        linkUrlClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it, StandardCharsets.UTF_8.toString())))
        },
        linkExternalBrowserClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it, StandardCharsets.UTF_8.toString())))
        },
        subredditClick = {},
        showError = {},
        hideSubClick = {},
        postHiddenFromView = {},
        postShownFromView = {},
        nextPost = {},
        clearVote = {},
        fullScreen = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), UserLogic(), Data.preview(), RedditPostListener.preview())
    }
}
