package com.simplicity.simplicityaclientforreddit.main.screen.comments

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CommentsLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<CommentResponse>>(UiState.Loading("Fetching comments"))
    val stateFlow: StateFlow<UiState<CommentResponse>> = _stateFlow

    fun init(postId: String, subreddit: String) {
        background {
            // Do something in the background
            val call = API(CommentResponse::class.java, CommentSerializer()).getComments(subreddit, postId)
            call.enqueue(object : CustomResponseCompose<ArrayList<CommentResponse>>(this) {
                override fun success(responseBody: ArrayList<CommentResponse>) {
                    Log.i(TAG, "Got comments $responseBody")
                    foreground {
                        _stateFlow.emit(UiState.Success(responseBody[1]))
                    }
                }
            })
        }
    }

    companion object {
        private const val TAG = "CommentsLogic"
    }
}
