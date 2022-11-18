package com.simplicity.simplicityaclientforreddit.main.screen.posts.single

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.FetchPostsResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.JsonResponse
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.AddCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.GetCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.RemoveCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.post.FilterPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SingleListLogic : BaseLogic() {
    private lateinit var navigationListener: NavigationListener
    var subReddit: String = ""
    private val _state = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val state: StateFlow<UiState<Data>> = _state

    private var _posts = ArrayList<RedditPost>()
    private var _cursor: String? = ""
    private var _positionOfCurrentPost = 0

    fun start(navigationListener: NavigationListener, subReddit: String = "") {
        this.navigationListener = navigationListener
        this.subReddit = subReddit
        if (_posts.size == 0) {
            background {
                if (subReddit.isEmpty()) {
                    val readPosts = GetCachedPostUseCase().execute()
                    Log.i(TAG, "We got this many cached posts: ${readPosts.size}")
                    _posts.addAll(readPosts)
                }
                showFirstPost()
                if (_posts.size < 4) {
                    fetchPosts()
                }
            }
        }
    }

    fun fetchPosts() {
        if (isFetching().value == false || isFetching().value == null) {
            background {
                _cursor?.let {
                    fetchPosts(it)
                }
            }
        }
    }

    private fun showFirstPost() {
        foreground {
            if (_posts.isNotEmpty()) {
                _posts.first().let { firstItem ->
                    RemoveCachedPostUseCase(firstItem).execute()
                    _state.emit(UiState.Success(Data(firstItem)))
                }
            }
        }
    }

    private fun fetchPosts(cursor: String) {
        setIsFetching(true)
        Log.i(TAG, "Getting reddit posts with this cursor: $cursor")
        val call = API().getPosts(subReddit.ifEmpty { "all" }, cursor, "on")
        call.enqueue(object : CustomResponseCompose<FetchPostsResponse>(this) {
            override fun success(responseBody: FetchPostsResponse) {
                handleResponse(responseBody)
            }
        })
    }

    private fun handleResponse(response: FetchPostsResponse) {
        val hasShownPostAlready = (_posts.size > 0)
        response.data.let { data ->
            _cursor = data.after
            data.children?.let { processFetchedPosts(it) }
            if (data.after == null || data.after.isEmpty()) {
                Log.i(TAG, "There is no posts to show.")
                _state.tryEmit(UiState.Error("There is no posts to show."))
            }
        }
        // Show first item if there is any if we have not already showed a cached post
        if (_posts.size > 0 && !hasShownPostAlready) {
            showFirstPost()
        }
        if ((_posts.size - _positionOfCurrentPost) < 8) {
            // Fetching next set of posts.
            _cursor?.let {
                fetchPosts(it)
            }
        } else {
            setIsFetching(false)
        }
    }

    private fun processFetchedPosts(children: List<RedditPost>) {
        for (child in children) {
            if (FilterPostsUseCase().canDisplay(child, true, true)) {
                _posts.add(child)
                if (subReddit.isEmpty()) { // We are in /all feed, we can cache these posts safely
                    AddCachedPostUseCase(child).execute()
                }
            }
        }
        Log.i(TAG, "We now got ${_posts.size} posts to show")
    }

    private fun updateUiWithNextItem() {
        foreground {
            if (_posts.isNotEmpty() && _posts.size > (_positionOfCurrentPost + 1)) {
                _positionOfCurrentPost++
                val postToShow = _posts[_positionOfCurrentPost]
                _state.tryEmit(UiState.Success(Data(postToShow)))
                RemoveCachedPostUseCase(postToShow).execute()
            }
            if ((_posts.size - _positionOfCurrentPost) < 4) {
                _cursor?.let { fetchPosts(it) }
            }
        }
    }

    fun upVote(it: RedditPost) {
        val call = APIAuthenticated().upVote("t3_${it.data.id}")
        call.enqueue(object : CustomResponseCompose<JsonResponse>(this) {
            override fun success(responseBody: JsonResponse) {
                Log.i(TAG, "UpVoted")
            }
        })
    }

    fun downVote(it: RedditPost) {
        val call = APIAuthenticated().downVote("t3_${it.data.id}")
        call.enqueue(object : CustomResponseCompose<JsonResponse>(this) {
            override fun success(responseBody: JsonResponse) {
                Log.i(TAG, "DownVote")
            }
        })
    }

    fun clearVote(post: RedditPost) {
    }

    fun nextPost() {
        updateUiWithNextItem()
    }

    fun previousPost() {
        foreground {
            if (_posts.isNotEmpty() && _positionOfCurrentPost > 1) {
                _positionOfCurrentPost--
                val postToShow = _posts[_positionOfCurrentPost]
                _state.tryEmit(UiState.Success(Data(postToShow)))
            }
        }
    }

    fun sharePost(redditPost: RedditPost) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, redditPost.data.url)
        navigationListener.navigate.invoke(shareIntent)
    }

    fun goToReddit(post: RedditPost) {
        val convertedUrl = "https://www.reddit.com${post.data.permalink}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
        navigationListener.navigate.invoke(browserIntent)
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
