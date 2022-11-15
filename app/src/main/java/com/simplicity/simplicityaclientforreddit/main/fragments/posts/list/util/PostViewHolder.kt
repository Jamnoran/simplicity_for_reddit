package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.util

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.RedditMedia
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes.MediaTypeVideo
import com.simplicity.simplicityaclientforreddit.main.listeners.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetFormattedTextUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase

class PostViewHolder(val listener: RedditPostListener, var binding: RedditPostBinding, val layoutInflater: LayoutInflater) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var redditMedia: RedditMedia
    var showed: Boolean = false
    private val TAG = "PostViewHolder"
    var post: RedditPost? = null

    /* Bind post data. */
    fun bind(post: RedditPost) {
        this.post = post

        binding.redditMedia.redditMediaHolder.removeAllViews()
        binding.root.tag = this
        redditMedia = RedditMedia(post, listener)
        redditMedia.init(binding, layoutInflater)

        setUpListeners(post)
        binding.redditSub.text = binding.root.context.getString(R.string.sub_reddit, post.data.subreddit)
        binding.redditPosted.text = binding.root.context.getString(R.string.post_posted_by, post.data.author)
        post.data.created?.let {
            binding.redditWhen.text = GetTimeAgoUseCase().execute(it)
        }

        if (post.data.postHint == "link") {
            binding.redditTitle.visibility = View.GONE
        } else {
            binding.redditTitle.text = binding.root.context.getString(R.string.reddit_title, GetFormattedTextUseCase(listener).execute(post.data.title ?: ""), post.data.postHint)
            binding.redditTitle.visibility = View.VISIBLE
        }
    }

    private fun setUpListeners(currentPost: RedditPost) {
        currentPost.let { post ->
            binding.redditTitle.setOnClickListener { listener.redditLinkClicked(post) }

            binding.redditSub.setOnClickListener { listener.subRedditClicked(post) }
            binding.redditPosted.setOnClickListener { listener.authorClicked(post) }

            binding.bottomLayout.hideSub.setOnClickListener { listener.hideSubClicked(post) }
        }
    }

//    fun getThumbnail(): ImageView? {
//        if (redditMedia.media is MediaTypeVideo) { return (redditMedia.media as MediaTypeVideo).mediaVideoPlayerBinding?.thumbnail }
//        return null
//    }

    fun getProgressBar(): ProgressBar? {
        if (redditMedia.media is MediaTypeVideo) { return (redditMedia.media as MediaTypeVideo).mediaVideoPlayerBinding?.progressBar }
        return null
    }

    fun getVolumeControl(): ImageView? {
        if (redditMedia.media is MediaTypeVideo) { return (redditMedia.media as MediaTypeVideo).mediaVideoPlayerBinding?.volumeControl }
        return null
    }

    fun getFrameLayout(): FrameLayout? {
        if (redditMedia.media is MediaTypeVideo) { return (redditMedia.media as MediaTypeVideo).mediaVideoPlayerBinding?.mediaContainer }
        return null
    }

    fun postInFocus(focus: Boolean) {
        redditMedia.postInFocus(focus)
    }
}
