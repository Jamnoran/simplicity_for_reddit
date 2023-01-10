package com.simplicity.simplicityaclientforreddit.main.screen.posts.single

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.JsonResponse
import com.simplicity.simplicityaclientforreddit.main.utils.RedditListLogic
import com.simplicity.simplicityaclientforreddit.main.utils.RedditListLogicListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SingleListLogic : BaseComposeLogic<SingleListInput>() {
    private lateinit var navigationListener: NavigationListener
    var subReddit: String = ""
    var redditListLogic: RedditListLogic = RedditListLogic()

    private val _state = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val state: StateFlow<UiState<Data>> = _state

    override fun ready(input: SingleListInput) {
        subReddit = input.subReddit
        val listener = RedditListLogicListener(
            activePost = {
                foreground {
                    _state.emit(UiState.Success(Data(it)))
                }
            },
            activePosts = {},
            error = {
                foreground {
                    _state.tryEmit(UiState.Error(it))
                }
            }
        )
        redditListLogic.init(subReddit = subReddit, api = API(), logic = this, listener = listener)
        background { redditListLogic.initList() }

        this.navigationListener = input.navigationListener
    }

    fun nextPost() {
        foreground { _state.emit(UiState.Success(Data(redditPost = null, scrollToTop = Unit))) }
        background {
            Thread.sleep(20)
            redditListLogic.showNextPost()
        }
    }

    fun previousPost() {
        foreground { _state.emit(UiState.Success(Data(redditPost = null, scrollToTop = Unit))) }
        background { redditListLogic.showPreviousPost() }
    }

    fun upVote(post: RedditPost.Data) {
        val call = APIAuthenticated().upVote("t3_${post.id}")
        call.enqueue(object : CustomResponseCompose<JsonResponse>(this) {
            override fun success(responseBody: JsonResponse) {
                Log.i(TAG, "UpVoted")
            }

            override fun failed(reason: String) {
                Log.e(TAG, "Failed $reason")
            }
        })
    }

    fun downVote(post: RedditPost.Data) {
        val call = APIAuthenticated().downVote("t3_${post.id}")
        call.enqueue(object : CustomResponseCompose<JsonResponse>(this) {
            override fun success(responseBody: JsonResponse) {
                Log.i(TAG, "DownVote")
            }

            override fun failed(reason: String) {
                Log.e(TAG, "Failed $reason")
            }
        })
    }

    fun clearVote(post: RedditPost.Data) {
        val call = APIAuthenticated().clearVote("t3_${post.id}")
        call.enqueue(object : CustomResponseCompose<JsonResponse>(this) {
            override fun success(responseBody: JsonResponse) {
                Log.i(TAG, "Cleared vote")
            }

            override fun failed(reason: String) {
                Log.e(TAG, "Failed $reason")
            }
        })
    }

    fun sharePost(post: RedditPost.Data) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
//        shareIntent.putExtra(Intent.EXTRA_TEXT, post.url)
//        shareIntent.putExtra(Intent.EXTRA_TEXT, post.urlOverriddenByDest)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.reddit.com${post.permalink}")
        navListener(shareIntent)
    }

    fun goToReddit(post: RedditPost.Data) {
        val convertedUrl = "https://www.reddit.com${post.permalink}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
        navListener(browserIntent)
    }

    fun hideReddit(subreddit: String) {
        background {
            RoomDB().hideSub(subreddit)
        }
    }

    companion object {
        private const val TAG = "SingleListLogic"
    }
}
