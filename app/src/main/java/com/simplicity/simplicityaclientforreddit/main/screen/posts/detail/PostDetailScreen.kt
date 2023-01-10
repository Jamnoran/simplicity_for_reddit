package com.simplicity.simplicityaclientforreddit.main.screen.posts.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.Post
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.theme.Shape
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.theme.Transparent
import com.simplicity.simplicityaclientforreddit.main.usecases.post.IsPostRequiringFulLScreenUseCase
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PostDetailScreen(navigator: NavHostController, logic: PostDetailLogic) {
    val uiState: UiState<RedditPost> by logic.stateFlow.collectAsStateWithLifecycle()
    uiState.let {
        when (it) {
            is UiState.Loading -> ScreenLoading(it.loadingMessage)
            is UiState.Error -> ScreenError()
            is UiState.Empty -> {}
            is UiState.Success -> Screen(
                navController = navigator,
                data = it.data,
                listener = getListener(logic, navigator)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Screen(
    navController: NavHostController,
    data: RedditPost,
    listener: RedditPostListener
) {
    val scrollState = rememberScrollState()
    val scrollingEnabled = !IsPostRequiringFulLScreenUseCase(data).execute()
    val modifier = if (scrollingEnabled) Modifier.verticalScroll(scrollState) else Modifier
    Column(modifier) {
        Post(post = data, listener)
        Spacer(modifier = Modifier.height(Shape.BOTTOM_NAV_HEIGHT))
    }
}

@Composable
fun BottomBar(modifier: Modifier, navigateToNext: () -> Unit, navigateToPrevious: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(modifier.fillMaxWidth()) {
        Box(
            Modifier
                .background(Transparent)
                .fillMaxWidth()
                .height(Shape.BOTTOM_NAV_HEIGHT)
                .weight(0.3f)
                .clickable(interactionSource = interactionSource, indication = null) { navigateToPrevious.invoke() }
        )
        Box(
            Modifier
                .background(Transparent)
                .fillMaxWidth()
                .height(Shape.BOTTOM_NAV_HEIGHT)
                .weight(0.7f)
                .clickable(interactionSource = interactionSource, indication = null) { navigateToNext.invoke() }
        )
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier, navigateToNext: () -> Unit, navigateToPrevious: () -> Unit, navigateToSettings: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(modifier.fillMaxWidth()) {
        Box(
            Modifier
                .background(Transparent)
                .fillMaxWidth()
                .height(Shape.BOTTOM_NAV_HEIGHT)
                .weight(1f)
                .clickable(interactionSource = interactionSource, indication = null) { navigateToPrevious.invoke() }
        )
        Box(
            Modifier
                .background(Transparent)
                .fillMaxWidth()
                .height(Shape.BOTTOM_NAV_HEIGHT)
                .weight(1f)
                .clickable(interactionSource = interactionSource, indication = null) { navigateToSettings.invoke() }
        )
        Box(
            Modifier
                .background(Transparent)
                .fillMaxWidth()
                .height(Shape.BOTTOM_NAV_HEIGHT)
                .weight(1f)
                .clickable(interactionSource = interactionSource, indication = null) { navigateToNext.invoke() }
        )
    }
}

fun getListener(logic: PostDetailLogic, navigator: NavHostController): RedditPostListener {
    return RedditPostListener(
        downVote = { logic.downVote(it) },
        upVote = { logic.upVote(it) },
        redditClick = {},
        authorClick = { it.author?.let { author -> navigator.navigate(NavRoute.USER.withArgs(author)) } },
        shareClick = {},
        readComments = { navigator.navigate(NavRoute.COMMENTS.withArgs(it.id, it.subreddit)) },
        linkClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())))
        },
        linkUrlClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it, StandardCharsets.UTF_8.toString())))
        },
        linkExternalBrowserClick = {
            logic.openBrowser(url = it)
        },
        subredditClick = { navigator.navigate(NavRoute.SINGLE_LIST.withArgs(it.subreddit)) },
        showError = {},
        hideSubClick = {},
        postHiddenFromView = {},
        nextPost = {},
        clearVote = {},
        fullScreen = {
            it.url?.let { imageUrl ->
                navigator.navigate(NavRoute.FULL_SCREEN_IMAGE.withArgs(URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Screen(
            navController = rememberNavController(),
            data = TesterHelper.getPost(),
            RedditPostListener.preview()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    SimplicityAClientForRedditTheme {
        BottomBar(
            modifier = Modifier,
            navigateToNext = { },
            navigateToPrevious = { }
        )
    }
}
