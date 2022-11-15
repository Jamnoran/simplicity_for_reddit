package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.listeners.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.user.IsLoggedInUseCase
import java.text.NumberFormat

class CustomPostBottomSectionView(val binding: RedditPostBinding, val post: RedditPost, var listener: RedditPostListener) {
    var ownVote = 0
    fun init() {
        updateButton(binding.bottomLayout.downVoteButton, R.drawable.down_arrow_disabled, R.color.post_vote_default_color)
        updateButton(binding.bottomLayout.upVoteButton, R.drawable.up_arrow_disabled, R.color.post_vote_default_color)
        showVoteNumber()
        showCommentNumbers()
        setUpListeners()
    }

    private fun updateButton(imageView: ImageView, drawable: Int, tintColor: Int) {
        imageView.setImageResource(drawable)
        imageView.setColorFilter(ContextCompat.getColor(binding.root.context, tintColor), android.graphics.PorterDuff.Mode.MULTIPLY)
    }

    private fun showCommentNumbers() {
        binding.bottomLayout.comments.text = binding.root.context.getString(R.string.comment_counter, NumberFormat.getInstance().format(post.data.numComments))
    }

    private fun setUpListeners() {
        binding.bottomLayout.let {
            if (IsLoggedInUseCase().execute()) {
                it.downVoteButton.setOnClickListener { downVote() }
                it.upVoteButton.setOnClickListener { upVote() }
                it.comments.setOnClickListener { listener.commentsClicked(post) }
                it.shareButton.setOnClickListener { listener.sharePost(post) }
            }
        }
    }

    private fun upVote() {
        ownVote = +1
        updateButton(binding.bottomLayout.upVoteButton, R.drawable.up_arrow_normal, R.color.post_vote_up_color)
        updateButton(binding.bottomLayout.downVoteButton, R.drawable.down_arrow_disabled, R.color.post_vote_default_color)
        showVoteNumber()
        listener.voteUp(post)
    }

    private fun downVote() {
        ownVote = -1
        updateButton(binding.bottomLayout.downVoteButton, R.drawable.down_arrow_normal, R.color.post_vote_down_color)
        updateButton(binding.bottomLayout.upVoteButton, R.drawable.up_arrow_disabled, R.color.post_vote_default_color)
        listener.voteDown(post)
        showVoteNumber()
    }

    private fun showVoteNumber() {
        val votes = (post.data.ups - post.data.downs) + ownVote
        binding.bottomLayout.votes.text = NumberFormat.getInstance().format(votes)
    }
}
