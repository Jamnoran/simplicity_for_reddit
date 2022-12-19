package com.simplicity.simplicityaclientforreddit.main.screen.comments

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.CommentFooter
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.Children
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditCommentListener
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.theme.Surface
import com.simplicity.simplicityaclientforreddit.main.theme.Tertiary
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase

@Composable
fun CommentsScreen(navController: NavHostController, logic: CommentsLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Error -> ScreenError()
        is UiState.Loading -> ScreenLoading()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, logic, state.data)
    }
}

@Composable
fun Show(navController: NavHostController?, logic: CommentsLogic, data: Data) {
    val commentList: List<ChildrenData>? = data.response.commentResponseData?.children?.map { it.childrenData!! }

    DefaultScreen {
        commentList?.let { listItems ->
            LazyColumn {
                items(listItems) { childData ->
                    Comment(childData)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Comment(comment: ChildrenData) {
    var showChildren by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        Modifier.fillMaxWidth().background(Surface).padding(8.dp).combinedClickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {},
            onLongClick = {
                showChildren = !showChildren
                Log.i("CommentsScreen", "Setting child visibility to : $showChildren")
            }
        )
    ) {
        Row() {
            // Author + time
            CText(text = "r/${comment.author ?: "[deleted]"}", color = Primary)
            CText(text = " ${GetTimeAgoUseCase().execute(comment.createdUtc)}")
        }
        // Comment
        CText(text = comment.body ?: "[deleted]")
        // Bottom bar
        val listener = RedditCommentListener({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
        CommentFooter(comment = comment, listener = listener)
        if (showChildren) {
            // Children
            ShowChildren(comment.repliesCustomParsed?.repliesData?.children)
        } else {
            CText("...........Long click to expand again..........")
        }
    }
}

@Composable
fun ShowChildren(replies: ArrayList<Children>?) {
    if (replies?.isNotEmpty() == true) {
        Row(Modifier.padding(start = 6.dp).fillMaxWidth()) {
            Column(Modifier.background(Tertiary).padding(start = 2.dp)) {
                for (reply in replies) {
                    reply.childrenData?.let { childData ->
                        Comment(childData)
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
        Show(rememberNavController(), CommentsLogic(), Data.preview())
    }
}
