package com.simplicity.simplicityaclientforreddit.main.screen.user

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomCallback
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.UserResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.posts.UserPostsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserLogic : BaseComposeLogic<UserInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    private var _userName = "jamnoran"
    private var _user: User? = null
    private var _cursor = ""
    private var _posts = ArrayList<RedditPost>()

    fun start(input: UserInput) {
        Log.i(TAG, "Init is called with username: $input.userName")
        _userName = input.userName
        background {
            fetchUser()
            fetchPosts()
        }
    }

    private fun fetchUser() {
        val call = APIAuthenticated().getUser(_userName)
        call.enqueue(
            object : CustomCallback<UserResponse> {
                override fun onSuccess(responseBody: UserResponse) {
                    responseBody.data.let { userData ->
                        _user = userData
                    }
                    updateUi()
                }

                override fun onUnauthorized() {
                    Log.e(TAG, "onUnauthorized")
                    networkError.postValue(Unit)
                }

                override fun onFailed(throwable: Throwable) {
                    Log.e(TAG, "Error: ", throwable)
                }
            }
        )
    }

    private fun updateUi() {
        foreground {
            _stateFlow.emit(UiState.Success(Data(user = _user, _posts)))
        }
    }

    private fun fetchPosts() {
        val call = APIAuthenticated().getUserPosts(_userName, _cursor)
        call.enqueue(object : Callback<UserPostsResponse> {
            override fun onResponse(
                call: Call<UserPostsResponse>,
                response: Response<UserPostsResponse>
            ) {
                response.body()?.data?.let { data ->
                    _cursor = data.after
                    processFetchedPosts(data.children)
                    updateUi()
                }
            }

            override fun onFailure(call: Call<UserPostsResponse>, t: Throwable) {
                Log.e(TAG, "Error : ", t)
                setIsFetching(false)
            }
        })
    }

    private fun processFetchedPosts(children: List<RedditPost>) {
        _posts.addAll(children)
    }

    companion object {
        private val TAG = "UserLogic"
    }
}
