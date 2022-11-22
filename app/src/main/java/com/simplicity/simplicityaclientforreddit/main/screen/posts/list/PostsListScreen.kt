package com.simplicity.simplicityaclientforreddit.main.screen.posts.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.menu.NavigationDrawer
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.Post
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PostsListScreen(navigator: NavHostController, logic: PostsListLogic) {
    val uiState: UiState<List<RedditPost>> by logic.stateFlow.collectAsStateWithLifecycle()
    uiState.let {
        when (it) {
            is UiState.Loading -> ScreenLoading(it.loadingMessage)
            is UiState.Error -> ScreenError()
            is UiState.Empty -> {}
            is UiState.Success -> Screen(navigator, it.data, getListener(logic, navigator))
        }
    }
}

@Composable
fun Screen(
    navigator: NavHostController,
    data: List<RedditPost>,
    listener: RedditPostListener
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { NavigationDrawer(navigator) {} }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            LazyColumn {
                items(data) {
                    Post(post = it, listener)
                }
            }
        }
    }
}

fun getListener(logic: PostsListLogic, navigator: NavHostController): RedditPostListener {
    return RedditPostListener(
        downVote = { logic.downVote(it) },
        upVote = { logic.upVote(it) },
        redditClick = {},
        authorClick = {},
        shareClick = {},
        readComments = {},
        linkClick = {
            val encodedUrl = URLEncoder.encode(it.data.url, StandardCharsets.UTF_8.toString())
//            navigator.navigate(LINK.plus("/").plus(encodedUrl))
//            navigator.navigate((navigator, encodedUrl))
        },
        subredditClick = {},
        showError = {},
        hideSubClick = {},
        postHidden = {},
        nextPost = {},
        clearVote = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Screen(
            rememberNavController(),
            listOf(TesterHelper.getPost(), TesterHelper.getPost()),
            RedditPostListener.preview()
        )
    }
}
