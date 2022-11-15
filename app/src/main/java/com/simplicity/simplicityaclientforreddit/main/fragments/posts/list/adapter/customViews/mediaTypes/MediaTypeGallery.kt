package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.simplicity.simplicityaclientforreddit.databinding.MediaGalleryLayoutBinding
import com.simplicity.simplicityaclientforreddit.databinding.MediaImageLayoutBinding
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.listeners.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GalleryItem
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetGalleryImageUrlUseCase

class MediaTypeGallery(post: RedditPost, binding: RedditPostBinding, layoutInflater: LayoutInflater, listener: RedditPostListener) : BaseMediaType(post, binding, layoutInflater, listener) {
    lateinit var mediaGalleryLayoutBinding: MediaGalleryLayoutBinding
    lateinit var primaryMediaGalleryLayoutBinding: MediaImageLayoutBinding
    lateinit var secondaryMediaGalleryLayoutBinding: MediaImageLayoutBinding

    override fun show() {
        mediaGalleryLayoutBinding = MediaGalleryLayoutBinding.inflate(layoutInflater)
        binding.redditMedia.redditMediaHolder.addView(mediaGalleryLayoutBinding.root)
        secondaryMediaGalleryLayoutBinding = MediaImageLayoutBinding.inflate(layoutInflater)
        binding.redditMedia.redditMediaHolder.addView(secondaryMediaGalleryLayoutBinding.root)
        primaryMediaGalleryLayoutBinding = MediaImageLayoutBinding.inflate(layoutInflater)
        binding.redditMedia.redditMediaHolder.addView(primaryMediaGalleryLayoutBinding.root)
        secondaryMediaGalleryLayoutBinding.root.visibility = View.GONE

//        binding.redditMedia.galleryLayout.root.visibility = View.VISIBLE
//        binding.redditMedia.galleryLayout.redditGalleryCount.visibility = View.VISIBLE
        var galleryCountText = ""
        post.data.mediaMetadata?.let { mediaMetaData ->
            galleryCountText = "1/${mediaMetaData.size}"
        }
        mediaGalleryLayoutBinding.redditGalleryCount.text = galleryCountText

        var position = 0
        var galleryData = GetGalleryImageUrlUseCase().execute(post.data, position)
        loadGallery(galleryData, mediaGalleryLayoutBinding.root.context)
        mediaGalleryLayoutBinding.navigateLeft.setOnClickListener {
            if (position > 0) position--
            galleryData = GetGalleryImageUrlUseCase().execute(post.data, position)
            Log.i("RedditMedia", "User clicked Left, showing picture on position $position with this image ${galleryData.current}")
            galleryData.current?.let {
                loadGallery(galleryData, binding.root.context)
                galleryCountText = "${(position + 1)}/${post.data.mediaMetadata?.size}"
                mediaGalleryLayoutBinding.redditGalleryCount.text = galleryCountText
            }
        }
        mediaGalleryLayoutBinding.navigateRight.setOnClickListener {
            if ((position + 1) < (post.data.mediaMetadata?.size ?: 0)) position++
            galleryData = GetGalleryImageUrlUseCase().execute(post.data, position)
            Log.i("RedditMedia", "User clicked Right, showing picture on position $position with this image ${galleryData.current}")
            galleryData.current?.let {
                loadGallery(galleryData, binding.root.context)
                galleryCountText = "${(position + 1)}/${post.data.mediaMetadata?.size}"
                mediaGalleryLayoutBinding.redditGalleryCount.text = galleryCountText
            }
        }
    }

    private fun loadGallery(galleryData: GalleryItem, context: Context) {
        loadImageOrGif(galleryData.current, context, primaryMediaGalleryLayoutBinding.redditImage)
        galleryData.next?.let {
            if (it.mediaUrl.isNotEmpty()) {
                preLoadImageOrGif(it, context, secondaryMediaGalleryLayoutBinding.redditImage)
            }
        }
    }
}
