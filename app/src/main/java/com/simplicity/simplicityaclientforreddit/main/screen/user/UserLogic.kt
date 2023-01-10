package com.simplicity.simplicityaclientforreddit.main.screen.user

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomCallback
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.posts.UserPostsResponse
import com.simplicity.simplicityaclientforreddit.main.utils.extensions.fold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserLogic : BaseComposeLogic<UserInput>() {
    private val _state = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val state: StateFlow<UiState<Data>> = _state

    private lateinit var _input: UserInput

    private var _user: User? = null
    private var _cursor = ""
    private var _posts = ArrayList<RedditPost>()

    override fun ready(input: UserInput) {
        _input = input
        Log.i(TAG, "Init is called with username: ${_input.userName}")
        background {
            fetchUser()
        }
    }

    private fun fetchUser() {
        val call = APIAuthenticated().getUser(_input.userName)
        call.fold(
            onSuccess = { responseBody ->
                responseBody.data.let { userData ->
                    _user = userData
                    fetchPosts()
                }
            },
            onFailure = {
                Log.e(TAG, "Error: ", it.throwable)
                foreground {
                    _state.emit(UiState.Error(it.throwable.toString()))
                }
            }
        )
    }

    private fun fetchPosts() {
        val call = APIAuthenticated().getUserPosts(_input.userName, _cursor)
        call.enqueue(object : CustomCallback<UserPostsResponse> {
            override fun onSuccess(responseBody: UserPostsResponse) {
                responseBody.data.let { data ->
                    _cursor = data.after
                    _posts.addAll(data.children)
                    Log.i("UserLogic", "Posts fetched, showing list with a size of ${_posts.size}")
                    updateUi()
                }
            }

            override fun onUnauthorized() {
                Log.e(TAG, "onUnauthorized")
                networkError.postValue(Unit)
                foreground {
                    _state.emit(UiState.Error("onUnauthorized"))
                }
            }

            override fun onFailed(throwable: Throwable) {
                Log.e(TAG, "Error : ", throwable)
                setIsFetching(false)
                foreground {
                    _state.emit(UiState.Error(throwable.toString()))
                }
            }
        })
    }

    fun goToReddit(post: RedditPost.Data) {
        val convertedUrl = "https://www.reddit.com${post.permalink}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
        navListener(browserIntent)
    }

    private fun updateUi() {
        foreground {
            Log.i("UserLogic", "Emitting posts $_posts")
            _state.emit(UiState.Success(Data(user = _user, _posts)))
        }
    }

    fun updateScreen() {
        updateUi()
    }

    companion object {
        private const val TAG = "UserLogic"
    }
}
