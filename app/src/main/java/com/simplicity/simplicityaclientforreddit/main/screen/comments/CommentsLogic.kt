package com.simplicity.simplicityaclientforreddit.main.screen.comments

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CommentsLogic : BaseComposeLogic<CommentsInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading("Fetching comments"))
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready(input: CommentsInput) {
        background {
            val call = API(CommentResponse::class.java, CommentSerializer()).getComments(input.subreddit, input.postId)
            call.enqueue(object : CustomResponseCompose<ArrayList<CommentResponse>>(this) {
                override fun success(responseBody: ArrayList<CommentResponse>) {
                    Log.i(TAG, "Got comments $responseBody")
                    foreground {
                        _stateFlow.emit(UiState.Success(Data(responseBody[1])))
                    }
                }

                override fun failed(reason: String) {
                    Log.e(TAG, "Failed because of reason $reason")
                }
            })
        }
    }

    fun downVote(childrenData: ChildrenData) {
    }

    fun upVote(childrenData: ChildrenData) {
    }

    fun clearVote(childrenData: ChildrenData) {
    }

    fun goToReddit(childrenData: ChildrenData) {
        val convertedUrl = "https://www.reddit.com${childrenData.permalink}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
        navListener(browserIntent)
    }

    fun sharePost(childrenData: ChildrenData) {
    }

    companion object {
        private const val TAG = "CommentsLogic"
    }
}
