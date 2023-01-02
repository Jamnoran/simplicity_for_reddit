package com.simplicity.simplicityaclientforreddit.main.utils

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.FetchPostsResponse
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.AddCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.GetCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.RemoveCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.post.FilterPostsUseCase
import retrofit2.Call

class RedditListLogic {
    private lateinit var _logic: BaseLogic
    private lateinit var _api: APIInterface
    private lateinit var _listener: RedditListLogicListener

    private var _posts = ArrayList<RedditPost>()
    private var _cursor: String? = ""
    private var _positionOfCurrentPost = 0
    private var _subReddit: String = ""

    fun init(subReddit: String, api: APIInterface, logic: BaseLogic, listener: RedditListLogicListener) {
        _subReddit = subReddit
        _api = api
        _logic = logic
        _listener = listener
    }

    fun initList() {
        if (_posts.size == 0) {
            if (_subReddit.isEmpty()) {
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

    private fun showFirstPost() {
        if (_posts.isNotEmpty()) {
            _posts.first().let { firstItem ->
                RemoveCachedPostUseCase(firstItem).execute()
                _listener.activePost.invoke(firstItem)
            }
        }
    }

    private fun fetchPosts() {
        Log.i(TAG, "Getting reddit posts with this cursor: $_cursor")
        val call = _api.getPosts(_subReddit.ifEmpty { "all" }, _cursor, "on")
        call.enqueue(object : CustomResponseCompose<FetchPostsResponse>(_logic) {
            override fun success(responseBody: FetchPostsResponse) {
                handleResponse(responseBody)
            }

            override fun failed(reason: String) {
                Log.i("RedditLogic", "onFailure")
                _listener.error.invoke("This is a private subreddit")
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
                _listener.error.invoke("There is no posts to show.")
            }
        }
        // Show first item if there is any if we have not already showed a cached post
        if (_posts.size > 0 && !hasShownPostAlready) {
            showFirstPost()
        }
        if ((_posts.size - _positionOfCurrentPost) < 8) {
            // Fetching next set of posts.
            _cursor?.let {
                fetchPosts()
            }
        }
    }

    private fun processFetchedPosts(children: List<RedditPost>) {
        for (child in children) {
            if (FilterPostsUseCase().canDisplay(child, true, true)) {
                _posts.add(child)
                if (_subReddit.isEmpty()) { // We are in /all feed, we can cache these posts safely
                    AddCachedPostUseCase(child).execute()
                }
            }
        }
        Log.i(TAG, "We now got ${_posts.size} posts to show")
        _listener.activePosts.invoke(_posts)
    }

    fun showNextPost() {
        if (_posts.isNotEmpty() && _posts.size > (_positionOfCurrentPost + 1)) {
            _positionOfCurrentPost++
            val postToShow = _posts[_positionOfCurrentPost]
            _listener.activePost.invoke(postToShow)
            RemoveCachedPostUseCase(postToShow).execute()
        }
        if ((_posts.size - _positionOfCurrentPost) < 4) {
            _cursor?.let { fetchPosts() }
        }
    }

    fun showPreviousPost() {
        if (_posts.isNotEmpty() && _positionOfCurrentPost > 1) {
            _positionOfCurrentPost--
            val postToShow = _posts[_positionOfCurrentPost]
            _listener.activePost.invoke(postToShow)
        }
    }

    fun getPosts(): ArrayList<RedditPost> {
        return _posts
    }

    companion object {
        const val TAG: String = "RedditListLogic"
    }
}

class RedditListLogicListener(
    val activePost: (post: RedditPost) -> Unit,
    val activePosts: (posts: List<RedditPost>) -> Unit,
    val error: (error: String) -> Unit
)
