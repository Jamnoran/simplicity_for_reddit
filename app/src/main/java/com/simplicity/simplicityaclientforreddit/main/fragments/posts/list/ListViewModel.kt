package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.MainFragmentBinding
import com.simplicity.simplicityaclientforreddit.main.base.BasePostsListViewModel
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.FetchPostsResponse
import com.simplicity.simplicityaclientforreddit.main.usecases.InitApplicationUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.AddCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.GetCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.post.FilterPostsUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.post.RemoveOldPostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel : BasePostsListViewModel<MainFragmentBinding>() {
    private val TAG = "ListViewModel"
    private var nsfwSettings = true
    private var sfwSettings = true
    private var subReddit: String? = null

    private val _redditPostsLiveData = MutableLiveData<ArrayList<RedditPost>>()
    private val _requireUpdateSettingsValues = MutableLiveData<Unit>()
    private val _showErrorMessage = MutableLiveData<Int>()

    fun posts(): LiveData<ArrayList<RedditPost>> {
        return _redditPostsLiveData
    }

    fun requireSettingsUpdate(): LiveData<Unit> {
        return _requireUpdateSettingsValues
    }

    fun showError(): LiveData<Int> {
        return _showErrorMessage
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

    fun setSettingsValues(nsfw: Boolean, sfw: Boolean) {
        nsfwSettings = nsfw
        sfwSettings = sfw
    }

    fun initSession(firstTimeApplicationStarted: Boolean) {
        _requireUpdateSettingsValues.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            InitApplicationUseCase().init(firstTimeApplicationStarted)
            RemoveOldPostsUseCase().removeOld()
        }
        if (subReddit == null) {
            _preLoadedPosts.addAll(GetCachedPostUseCase().execute())
        }
    }

    fun setSubReddit(it: String) {
        _preLoadedPosts.clear()
        subReddit = it
    }

    private fun fetchPosts(cursor: String) {
        _requireUpdateSettingsValues.postValue(Unit)
        setIsFetching(true)
        Log.i(TAG, "Getting reddit posts with this cursor: $cursor")
        val service = com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)
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
                Log.i(TAG, "There is no posts to show.")
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
        _redditPostsLiveData.postValue(_activePosts)
        _preLoadedPosts.clear()
    }
}
