package com.simplicity.simplicityaclientforreddit.main.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.simplicity.simplicityaclientforreddit.databinding.MediaVideoPlayerBinding
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.JsonResponse
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.AddSubRedditVisitedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BasePostsListViewModel<VB : ViewBinding> : BaseViewModel<VB>() {

    private val _openCommentsFragment = MutableLiveData<RedditPost>()
    private val _hideSubLiveData = MutableLiveData<Unit>()
    private val _subRedditClicked = MutableLiveData<String>()
    private val _authorClicked = MutableLiveData<String>()
    private val _browserUrl = MutableLiveData<String>()
    private val _webViewActivity = MutableLiveData<String>()
    private val _redditUrl = MutableLiveData<String>()
    private val _scrollToNextItem = MutableLiveData<Unit>()
    private val _shareRedditClicked = MutableLiveData<RedditPost>()
    private val _initVideoPlayer = MutableLiveData<MediaVideoPlayerBinding>()

    val _activePosts = ArrayList<RedditPost>()
    val _preLoadedPosts = ArrayList<RedditPost>()
    var _cursor: String? = ""

    fun hideSub(): LiveData<Unit> {
        return _hideSubLiveData
    }

    fun subRedditClicked(): LiveData<String> {
        return _subRedditClicked
    }

    fun authorClicked(): LiveData<String> {
        return _authorClicked
    }

    fun browserUrlClicked(): LiveData<String> {
        return _browserUrl
    }

    fun openCommentsFragment(): LiveData<RedditPost> {
        return _openCommentsFragment
    }

    fun shareRedditClicked(): LiveData<RedditPost> {
        return _shareRedditClicked
    }

    fun redditUrlClicked(): LiveData<String> {
        return _redditUrl
    }

    fun webViewActivityClicked(): LiveData<String> {
        return _webViewActivity
    }

    fun scrollToNextItem(): LiveData<Unit> {
        return _scrollToNextItem
    }

    fun initVideoPlayer(): LiveData<MediaVideoPlayerBinding> {
        return _initVideoPlayer
    }

    open fun fetchPosts() { }

    fun hideSub(subName: String) {
        _hideSubLiveData.postValue(Unit)
        viewModelScope.launch(Dispatchers.IO) {
            val db = RoomDB()
            db.hideSub(subName)
        }
    }

    fun upVote(post: RedditPost) {
        post.data.name.let { id ->
            APIAuthenticated().upVote(id).enqueue(object : Callback<JsonResponse> {
                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    response.body().let { data ->
                        Log.i(TAG, "Data $data")
                    }
                }

                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                    Log.e(TAG, "Error : ", t)
                }
            })
        }
    }

    fun downVote(post: RedditPost) {
        post.data.name.let { id ->
            APIAuthenticated().downVote(id).enqueue(object : Callback<JsonResponse> {
                override fun onResponse(
                    call: Call<JsonResponse>,
                    response: Response<JsonResponse>
                ) {
                    response.body().let { data ->
                        Log.i(TAG, "Data $data")
                    }
                }

                override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                    Log.e(TAG, "Error : ", t)
                }
            })
        }
    }

    fun scrollToNext(nextPosition: Int) {
        if (nextPosition >= (_activePosts.size - 3)) {
            fetchPosts()
        }
        _scrollToNextItem.postValue(Unit)
    }

    fun commentsClicked(post: RedditPost) {
        _openCommentsFragment.postValue(post)
    }

    fun subRedditClicked(subreddit: String) {
        AddSubRedditVisitedUseCase(subreddit).execute()
        _subRedditClicked.postValue(subreddit)
    }

    fun authorClicked(author: String) {
        _authorClicked.postValue(author)
    }

    fun linkClicked(post: RedditPost) {
//        when (GetPostTypeUseCase().execute(post.data)) {
//            PostType.IMGUR_LINK -> {
//                post.data.url?.let { link -> _browserUrl.postValue(link) }
// //                it.data.url?.let{ (activity as MainActivity).startWebViewActivity(it) }
//                return
//            }
//            PostType.LINK -> {
//                post.data.url?.let { url ->
//                    _webViewActivity.postValue(url)
// //                    (activity as MainActivity).startWebViewActivity(url)
//                }
//                return
//            }
//            PostType.IMAGE -> post.data.permalink?.let { _redditUrl.postValue(it) }
//            PostType.RICH_VIDEO -> post.data.permalink?.let { _redditUrl.postValue(it) }
//            PostType.IS_VIDEO -> post.data.permalink?.let { _redditUrl.postValue(it) }
//            PostType.GALLERY -> post.data.permalink?.let { _redditUrl.postValue(it) }
//            PostType.NONE -> {
//                when {
//                    post.data.url != null -> {
//                        _webViewActivity.postValue(post.data.url!!)
//                    }
//                    post.data.permalink != null -> {
//                        post.data.permalink?.let { _redditUrl.postValue(it) }
//                    }
//                    else -> {
//                        Log.i("ListFragment", "Could not find click method for this post $post")
//                    }
//                }
//            }
//            PostType.TOURNAMENT -> {}
//            PostType.YOUTUBE -> {
//                Log.i("ListFragment", "YOUTUBE IS NOT HANDLED CORRECTLY NOW")
//            }
//            else -> {}
//        }
    }

    fun directLinkClicked(link: String) {
        _webViewActivity.postValue(link)
    }

    fun redditLinkClicked(post: RedditPost) {
        post.data.permalink?.let { _redditUrl.postValue(it) }
    }

    fun sharePostClicked(reddit: RedditPost) {
        _shareRedditClicked.postValue(reddit)
    }

    fun initVideoPlayer(reddit: RedditPost, binding: MediaVideoPlayerBinding) {
        _initVideoPlayer.postValue(binding)
    }

    fun log(log: String) {
        Log.i("[${this::class.java.simpleName}]", log)
    }

    companion object {
        private const val TAG = "BasePostsListViewModel"
    }
}
