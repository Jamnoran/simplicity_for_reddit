package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.util.PostViewHolder
import com.simplicity.simplicityaclientforreddit.main.listeners.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts.RemoveCachedPostUseCase

class RedditListAdapter(val listener: RedditPostListener, val layoutInflater: LayoutInflater) : ListAdapter<RedditPost, PostViewHolder>(PostDiffCallback) {

    /* Creates and inflates view and return FlowerViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = RedditPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(listener, binding, layoutInflater)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewDetachedFromWindow(holder: PostViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.binding.redditTitle.text?.let {
            if (holder.showed) {
                Log.i("RedditListAdapter", ">>: $it")
                holder.post?.let { post ->
                    RemoveCachedPostUseCase(post).execute()
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: PostViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.showed = true
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<RedditPost>() {
    override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
        return oldItem.data.id == newItem.data.id
    }
}
