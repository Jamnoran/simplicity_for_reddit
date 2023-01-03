package com.simplicity.simplicityaclientforreddit.main.screen.posts.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseCompose
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseListCompose
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.FetchPostsResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.AddCachedPostUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.post.FilterPostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostsListLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<List<RedditPost>>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<List<RedditPost>>> = _stateFlow

    private val _redditPostsLiveData = MutableLiveData<ArrayList<RedditPost>>()
    private val _requireUpdateSettingsValues = MutableLiveData<Unit>()

    val _activePosts = ArrayList<RedditPost>()
    val _preLoadedPosts = ArrayList<RedditPost>()
    var _cursor: String? = ""
    var subReddit: String? = null

    private val singlePostUrl = getPostUrlVideoWithSound()
    fun getPostUrlImage(): String =
        "/r/sweden/comments/xxqy0a/det_är_fredag_mina_bekanta_bör_vi_skicka_våra/"

    fun getPostUrlYoutube(): String =
        "/r/videos/comments/xxls7u/im_a_voice_actor_i_edited_the_super_mario_bros/"

    fun getPostUrlGif(): String = "/r/gifs/comments/xvtkcc/spiderman/"
    fun getPostUrlGallery(): String =
        "/r/valheim/comments/xyjvkw/first_build_that_i_put_a_few_hours_into/"

    fun getPostUrlText(): String = "/r/homeassistant/comments/xxo8z5/thread_support_working/"
    fun getPostUrlLink(): String =
        "/r/science/comments/xyfwjf/phone_snubbing_your_partner_can_lead_to_a_vicious/"

    fun getPostUrlVideoWithSound(): String =
        "/r/aww/comments/xym0km/i_literally_felt_my_heart_melt_as_she_nodded_off/"

    fun start() {
        viewModelScope.launch(Dispatchers.Default) {
            fetchPost(singlePostUrl)
        }
    }

    fun fetchPosts() {
        if (isFetching().value == false || isFetching().value == null) {
            // check if we have cached results
            Log.i(Companion.TAG, "Preloaded array has a size of ${_preLoadedPosts.size}")
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

    private fun fetchPost(singlePostUrl: String) {
        val logic = this
        API(CommentResponse::class.java, CommentSerializer()).getPost(
            singlePostUrl
        ).enqueue(object : CustomResponseListCompose<CommentResponse>(logic) {
            override fun success(responseBody: ArrayList<CommentResponse>) {
                responseBody.let { commentsResponse ->
                    commentsResponse.first().redditPost?.let { redditPost ->
                        _stateFlow.tryEmit(UiState.Success(listOf(redditPost)))
                    }
                }
            }
        })
    }

    private fun fetchPosts(cursor: String) {
        _requireUpdateSettingsValues.postValue(Unit)
        setIsFetching(true)
        Log.i(TAG, "Getting reddit posts with this cursor: $cursor")
        val service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)
        val call = service.getPosts(subReddit ?: "all", cursor, "on")
        call.enqueue(object : CustomResponseCompose<FetchPostsResponse>(this) {
            override fun success(responseBody: FetchPostsResponse) {
                handleResponse(responseBody)
            }

            override fun failed(reason: String) {
                Log.e(TAG, "Failed because of reason $reason")
            }
        })
    }

    private fun handleResponse(response: FetchPostsResponse) {
        response.data.let { data ->
            _cursor = data.after
            data.children?.let { processFetchedPosts(it) }
            if (data.after == null || data.after.isEmpty()) {
                Log.i(TAG, "There is no posts to show.")
//                _showErrorMessage.postValue(R.string.error_empty)
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
            if (FilterPostsUseCase().canDisplay(child, true, true)) {
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

    fun upVote(it: RedditPost) {
        Log.i("PostListLogic", "UpVote ${it.data.author}")
    }

    fun downVote(it: RedditPost) {
        Log.i("PostListLogic", "downVote ${it.data.author}")
    }

    companion object {
        private val TAG = "PostListLogic"
    }
}
