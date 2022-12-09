package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes

import android.view.LayoutInflater
import android.view.View
import com.simplicity.simplicityaclientforreddit.databinding.MediaLinkLayoutBinding
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.listeners.OldRedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.squareup.picasso.Picasso

class MediaTypeLink(post: RedditPost, binding: RedditPostBinding, layoutInflater: LayoutInflater, listener: OldRedditPostListener) : BaseMediaType(post, binding, layoutInflater, listener) {
    lateinit var mediaLinkLayoutBinding: MediaLinkLayoutBinding
    override fun show() {
        mediaLinkLayoutBinding = MediaLinkLayoutBinding.inflate(layoutInflater)
        binding.redditMedia.redditMediaHolder.addView(mediaLinkLayoutBinding.root)

        setUpListeners()

        mediaLinkLayoutBinding.redditLinkDescription.visibility = View.VISIBLE
        mediaLinkLayoutBinding.redditLinkSrc.visibility = View.VISIBLE

        post.data.title?.let {
//            mediaLinkLayoutBinding.redditLinkDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
//            } else {
//                Html.fromHtml(it)
//            }
        }
//        post.data.domain?.let { mediaLinkLayoutBinding.redditLinkSrc.text = it }

        post.data.thumbnail?.let {
            mediaLinkLayoutBinding.redditLinkPreview.visibility = View.VISIBLE
            val picasso = Picasso.get()
//            picasso.load(it)
//                .into(mediaLinkLayoutBinding.redditLinkPreview)

//            Log.i("RedditMedia", "Found image with urlOverriddenByDest $it")
        }
    }

    private fun setUpListeners() {
        mediaLinkLayoutBinding.redditLinkDescription.setOnClickListener { listener.redditLinkClicked(post) }
        mediaLinkLayoutBinding.redditLinkPreview.setOnClickListener { listener.linkClicked(post) }
        mediaLinkLayoutBinding.redditLinkSrc.setOnClickListener { listener.linkClicked(post) }
    }
}
