package com.simplicity.simplicityaclientforreddit.main.fragments.comments

import android.os.Bundle
import android.view.View
import com.simplicity.simplicityaclientforreddit.databinding.CommentsFragmentBinding
import com.simplicity.simplicityaclientforreddit.main.base.BaseActivity
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse

class CommentsFragment(var subreddit: String, var postId: String, var author: String) : BaseTestFragment<CommentsFragmentBinding, CommentsViewModel>(
    CommentsViewModel::class.java,
    CommentsFragmentBinding::inflate
) {
    private val TAG: String = "CommentsFragment"

    override fun ready(savedInstanceState: Bundle?) {
        setUpAdapter(binding.recyclerView, resources, requireContext())
        viewModel.fetchComments(subreddit, postId)
        viewModel.comments().observe(requireActivity()) {
            observeComments(it)
        }
        viewModel.isFetching().observe(requireActivity()) { observeLoading(it) }
        (activity as BaseActivity).logFirebaseEvent("fragment_comments", TAG, "logged_in_false")
    }

    private fun observeLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        } else {
            binding.loadingBar.visibility = View.GONE
        }
    }

    private fun observeComments(comments: CommentResponse) {
        comments.commentResponseData?.let {
            val wrappedList = ArrayList<CommentViewHolder>()
            for (child in it.children) {
                wrappedList.add(CommentViewHolder(CommentViewData(child), viewModel, author))
            }

            submitList(wrappedList as ArrayList<Any>)
        }
    }

    companion object {
        fun newInstance(subreddit: String, postId: String, author: String) = CommentsFragment(subreddit, postId, author)
    }
}
