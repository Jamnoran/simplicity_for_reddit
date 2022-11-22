package com.simplicity.simplicityaclientforreddit.main.screen.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.posts.post.CommentFooter
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.Children
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditCommentListener
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.theme.Surface
import com.simplicity.simplicityaclientforreddit.main.theme.Tertiary
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase

@Composable
fun CommentsScreen(logic: CommentsLogic) {
    logic.stateFlow.collectAsState().value.let { state ->
        when (state) {
            is UiState.Loading -> ScreenLoading(state.loadingMessage)
            is UiState.Error -> ScreenError()
            is UiState.Empty -> {}
            is UiState.Success -> Show(state.data)
        }
    }
}

@Composable
fun Show(data: CommentResponse) {
    DefaultScreen(modifier = Modifier.background(Background)) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            data.commentResponseData?.children?.let { children ->
                for (reply in children) {
                    reply.childrenData?.let { childData ->
                        Comment(childData)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Comment(comment: ChildrenData) {
    Column(Modifier.fillMaxWidth().background(Surface).padding(8.dp)) {
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
        // Children
        ShowChildren(comment.repliesCustomParsed?.repliesData?.children)
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
        Show(TesterHelper.getComments())
    }
}
