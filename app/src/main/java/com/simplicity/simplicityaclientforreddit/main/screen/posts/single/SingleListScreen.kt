package com.simplicity.simplicityaclientforreddit.main.screen.posts.single

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.Post
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenEmpty
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.screen.posts.detail.BottomNavigationBar
import com.simplicity.simplicityaclientforreddit.main.theme.Shape
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.usecases.post.IsPostRequiringFulLScreenUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.AddSubRedditVisitedUseCase
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SingleListScreen(navigator: NavHostController, logic: SingleListLogic, uiState: UiState<Data>) {
    val listener = getListener(logic, navigator)
    when (uiState) {
        is UiState.Loading -> ScreenLoading(uiState.loadingMessage)
        is UiState.Error -> ScreenError(uiState.errorMessage)
        is UiState.Empty -> {
            ScreenEmpty()
        }
        is UiState.Success -> Screen(
            navigator = navigator,
            data = uiState.data,
            listener = listener,
            nextItem = { logic.nextPost() },
            previousItem = { logic.previousPost() }
        )
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
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scrollingEnabled = !IsPostRequiringFulLScreenUseCase(data.redditPost).execute()
    data.scrollToTop?.let {
        coroutineScope.launch {
            scrollState.scrollTo(0)
        }
    }
    DefaultScreen(Modifier) {
        Box(
            Modifier.fillMaxSize()
        ) {
            val modifier = if (scrollingEnabled) Modifier.verticalScroll(scrollState) else Modifier
            Column(modifier) {
                data.redditPost?.let { Post(post = it, listener) }
                Spacer(modifier = Modifier.height(Shape.BOTTOM_NAV_HEIGHT))
            }
            BottomNavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                navigateToNext = {
                    listener.postHiddenFromView.invoke()
                    nextItem.invoke()
                    Log.i("TestScreen", "Next")
                },
                navigateToPrevious = {
                    listener.postHiddenFromView.invoke()
                    previousItem.invoke()
                    Log.i("TestScreen", "Previous")
                },
                navigateToSettings = {
                    Log.i("TestScreen", "Open Settings")
                    navigator.navigate(NavRoute.MENU.path)
                }
            )
        }
    }
}

fun getListener(logic: SingleListLogic, navigator: NavHostController): RedditPostListener {
    return RedditPostListener(
        downVote = { logic.downVote(it) },
        upVote = { logic.upVote(it) },
        clearVote = { logic.clearVote(it) },
        redditClick = { logic.goToReddit(it) },
        authorClick = { it.author?.let { author -> navigator.navigate(NavRoute.USER.withArgs(author)) } },
        shareClick = { logic.sharePost(it) },
        readComments = { navigator.navigate(NavRoute.COMMENTS.withArgs(it.id, it.subreddit)) },
        linkClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())))
        },
        linkUrlClick = {
            navigator.navigate(NavRoute.WEB_VIEW.withArgs(URLEncoder.encode(it, StandardCharsets.UTF_8.toString())))
        },
        linkExternalBrowserClick = { logic.openBrowser(url = it) },
        subredditClick = {
            AddSubRedditVisitedUseCase(it.subreddit).execute()
            navigator.navigate(NavRoute.SINGLE_LIST.withArgs(it.subreddit))
        },
        showError = { },
        hideSubClick = { logic.hideReddit(it.subreddit) },
        postHiddenFromView = {},
        nextPost = { logic.nextPost() },
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
        Screen(rememberNavController(), Data(TesterHelper.getPost()), RedditPostListener.preview(), nextItem = {}, previousItem = {})
    }
}
