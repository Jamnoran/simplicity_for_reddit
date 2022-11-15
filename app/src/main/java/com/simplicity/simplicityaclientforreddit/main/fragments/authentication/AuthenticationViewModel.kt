package com.simplicity.simplicityaclientforreddit.main.fragments.authentication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.databinding.AuthenticationFragmentBinding
import com.simplicity.simplicityaclientforreddit.main.base.BaseViewModel
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel : BaseViewModel<AuthenticationFragmentBinding>() {

    private val _post = MutableLiveData<RedditPost>()

    fun post(): MutableLiveData<RedditPost> {
        return _post
    }

    fun parsePost(json: String) {
        _post.postValue(Gson().fromJson(json, RedditPost::class.java))
    }

    fun testDatabase(redditPost: RedditPost) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = RoomDB()

//            db.toReadPost(redditPost)?.let{
//                Log.i("DetailViewModel","Post to save to db: $it")
//                db.insertPost(it)
//            }
            db.deleteAllOlderThanAWeek()

            for (post in db.getAll()) {
                Log.i("DetailViewModel", "Post in db: $post")
            }
        }
    }
}
