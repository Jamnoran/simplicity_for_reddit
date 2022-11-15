package com.simplicity.simplicityaclientforreddit.main.fragments.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simplicity.simplicityaclientforreddit.databinding.CommentsFragmentBinding
import com.simplicity.simplicityaclientforreddit.main.base.BasePostsListViewModel
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.CustomResponseList
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse

class CommentsViewModel : BasePostsListViewModel<CommentsFragmentBinding>() {

    private val TAG: String = "CommentsViewModel"

    private val _comments = MutableLiveData<CommentResponse>()

    fun comments(): LiveData<CommentResponse> {
        return _comments
    }

    fun fetchComments(subreddit: String, postId: String) {
        API(CommentResponse::class.java, CommentSerializer()).getComments(subreddit, postId)
            .enqueue(object : CustomResponseList<CommentResponse>(this) {
                override fun success(responseBody: ArrayList<CommentResponse>) {
                    responseBody.let { commentsResponse ->
                        _comments.postValue(commentsResponse[1])
                    }
                }
            })
    }
}
