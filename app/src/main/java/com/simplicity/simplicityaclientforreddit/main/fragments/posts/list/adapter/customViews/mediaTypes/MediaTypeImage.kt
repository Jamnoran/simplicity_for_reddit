package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes

import android.view.LayoutInflater
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.listeners.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class MediaTypeImage(post: RedditPost, binding: RedditPostBinding, layoutInflater: LayoutInflater, listener: RedditPostListener) : BaseMediaType(post, binding, layoutInflater, listener) {
    override fun show() {
//        post.data.urlOverriddenByDest?.let { url ->
//            val mediaImageLayoutBinding: MediaImageLayoutBinding = MediaImageLayoutBinding.inflate(layoutInflater)
//            binding.redditMedia.redditMediaHolder.addView(mediaImageLayoutBinding.root)
//
//            if (url.contains(".gif")) {
//                loadGif(url, mediaImageLayoutBinding.root.context, mediaImageLayoutBinding.redditImage)
//            } else {
//                post.data.preview?.images?.first()?.source?.let { source ->
//                    loadImage(MediaData(url, GetGalleryImageUrlUseCase().getRatio(source.height, source.width), null, MediaBaseValues(source.height, source.width)), mediaImageLayoutBinding.redditImage)
//                }
//            }
//        }
    }
}
