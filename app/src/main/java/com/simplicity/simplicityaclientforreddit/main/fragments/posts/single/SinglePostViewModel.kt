package com.simplicity.simplicityaclientforreddit.main.fragments.posts.single

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.FragmentSinglePostBinding
import com.simplicity.simplicityaclientforreddit.main.base.BasePostsListViewModel
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponse
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseList
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.FetchPostsResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.AddCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.post.FilterPostsUseCase

class SinglePostViewModel : BasePostsListViewModel<FragmentSinglePostBinding>() {

    private val _activePost = MutableLiveData<RedditPost>()
    private val _requireUpdateSettingsValues = MutableLiveData<Unit>()
    private val _showErrorMessage = MutableLiveData<Int>()

    private var subReddit: String? = null
    private var nsfwSettings = true
    private var sfwSettings = true

    fun post(): LiveData<RedditPost> {
        return _activePost
    }

    fun parsePost(json: String) {
        _activePost.postValue(Gson().fromJson(json, RedditPost::class.java))
    }

    fun requireSettingsUpdate(): LiveData<Unit> {
        return _requireUpdateSettingsValues
    }

    fun setSubReddit(it: String) {
        _preLoadedPosts.clear()
        subReddit = it
    }

    fun fetchPost(singlePostUrl: String) {
        log("Fetching post with this url $singlePostUrl")
        API(CommentResponse::class.java, CommentSerializer()).getPost(
            singlePostUrl
        ).enqueue(object : CustomResponseList<CommentResponse>(this) {
            override fun success(responseBody: ArrayList<CommentResponse>) {
                responseBody.let { commentsResponse ->
//                    commentsResponse.first().redditPost?.let { redditPost ->
//                        _activePost.postValue(redditPost)
//                    }
                }
            }
        })
    }

    override fun fetchPosts() {
        if (isFetching().value == false || isFetching().value == null) {
            // check if we have cached results
            Log.i(TAG, "Preloaded array has a size of ${_preLoadedPosts.size}")
            if (_preLoadedPosts.isNotEmpty()) {
                // post those to list
                addPreloadedToList()
            }
            // Fetch more
            _cursor?.let {
                fetchPosts(it)
            }
        }
    }

    private fun fetchPosts(cursor: String) {
        _requireUpdateSettingsValues.postValue(Unit)
        setIsFetching(true)
        Log.i(TAG, "Getting reddit posts with this cursor: $cursor")
        val service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)
        val call = service.getPosts(subReddit ?: "all", cursor, "on")
        call.enqueue(object : CustomResponse<FetchPostsResponse>(this) {
            override fun success(responseBody: FetchPostsResponse) {
                handleResponse(responseBody)
            }
        })
    }

    private fun handleResponse(response: FetchPostsResponse) {
        response.data.let { data ->
            _cursor = data.after
            data.children?.let { processFetchedPosts(it) }
            if (data.after == null || data.after.isEmpty()) {
                Log.i(Companion.TAG, "There is no posts to show.")
                _showErrorMessage.postValue(R.string.error_empty)
            }
        }
        if (_preLoadedPosts.size < 8) {
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
            if (FilterPostsUseCase().canDisplay(child, nsfwSettings, sfwSettings)) {
                _preLoadedPosts.add(child)
                if (subReddit == null) { // We are in /all feed, we can cache these posts safely
                    AddCachedPostUseCase(child).execute()
                }
            }
        }
        if (_activePosts.isEmpty()) { // Nothing has been displayed before, show what we have and fetch more.
            addPreloadedToList()
        }
    }

    private fun addPreloadedToList() {
        _activePosts.addAll(_preLoadedPosts)
        _preLoadedPosts.clear()
        showNextPost()
    }

    fun showNextPost() {
        if (_activePosts.isNotEmpty()) {
            _activePost.postValue(_activePosts.first())
            _activePosts.removeFirst()
            _cursor?.let {
                if (_activePosts.size < 5) {
                    fetchPosts(it)
                }
            }
        }
    }

    companion object {
        private val TAG = SinglePostViewModel::class.java.name
    }
}
