package com.simplicity.simplicityaclientforreddit.main.screen.posts.single

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.menu.NavigationDrawer
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.Post
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.Loading
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.screen.posts.detail.BottomBar
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.AddSubRedditVisitedUseCase
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SingleListScreen(navigator: NavHostController, logic: SingleListLogic, uiState: UiState<Data>) {
    val listener = getListener(logic, navigator)
    uiState.let {
        when (it) {
            is UiState.Loading -> Loading(it.loadingMessage)
            is UiState.Error -> Error()
            is UiState.Success -> Screen(
                navigator = navigator,
                data = it.data,
                listener = listener,
                nextItem = { logic.nextPost() },
                previousItem = { logic.previousPost() }
            )
        }
    }
}

@Composable
fun Screen(
    navigator: NavHostController,
    data: Data,
    listener: RedditPostListener,
    nextItem: () -> Unit,
    previousItem: () -> Unit
) {
    Log.i("SingleListScreen", "Showing post with url : https://www.reddit.com${data.redditPost.data.permalink}")
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { NavigationDrawer(navigator) }
    ) { paddingValues ->
        DefaultScreen(Modifier.padding(paddingValues)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Post(post = data.redditPost, listener)
                    Spacer(modifier = Modifier.height(100.dp))
                }
                BottomBar(
                    Modifier.align(Alignment.BottomCenter),
                    navigateToNext = {
                        listener.postHidden.invoke()
                        nextItem.invoke()
                    },
                    navigateToPrevious = {
                        listener.postHidden.invoke()
                        previousItem.invoke()
                    }
                )
            }
        }
    }
}

fun getListener(logic: SingleListLogic, navigator: NavHostController): RedditPostListener {
    return RedditPostListener(
        downVote = { logic.downVote(it) },
        upVote = { logic.upVote(it) },
        redditClick = { logic.goToReddit(it) },
        authorClick = { it.data.author?.let { author -> navigator.navigate(NavRoute.USER.withArgs(author)) } },
        shareClick = { logic.sharePost(it) },
        readComments = { navigator.navigate(NavRoute.COMMENTS.withArgs(it.data.id, it.data.subreddit)) },
        linkClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it.data.url, StandardCharsets.UTF_8.toString())))
        },
        subredditClick = {
            AddSubRedditVisitedUseCase(it.data.subreddit).execute()
            navigator.navigate(NavRoute.SINGLE_LIST.withArgs(it.data.subreddit))
        },
        showError = { },
        hideSubClick = { logic.hideReddit(it.data.subreddit) },
        postHidden = {},
        nextPost = { logic.nextPost() }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Screen(
            rememberNavController(),
            Data(TesterHelper.getPost()),
            RedditPostListener.preview(),
            nextItem = {},
            previousItem = {}
        )
    }
}
