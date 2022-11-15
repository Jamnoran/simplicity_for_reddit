package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews

import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes.*
import com.simplicity.simplicityaclientforreddit.main.listeners.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.internal.enums.PostType
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetFormattedTextUseCase

class RedditMedia(val post: RedditPost, val listener: RedditPostListener) {
    private lateinit var inflater: LayoutInflater
    lateinit var binding: RedditPostBinding
    var media: BaseMediaType? = null

    fun init(postBinding: RedditPostBinding, layoutInflater: LayoutInflater) {
        inflater = layoutInflater
        binding = postBinding
        media = null
        clearViewsToDefaultValues()
        setUpBottom()
        setUpDebug(post)
        setUpMainPost()
    }

    private fun setUpMainPost() {
        post.data.let { data ->
            // Show if data exists
            when (GetPostTypeUseCase().execute(data)) {
                PostType.LINK -> {
                    media = MediaTypeLink(post, binding, inflater, listener)
                }
                PostType.GALLERY -> {
                    media = MediaTypeGallery(post, binding, inflater, listener)
                }
                PostType.IMAGE -> {
                    media = MediaTypeImage(post, binding, inflater, listener)
                }
                PostType.IS_VIDEO -> {
                    media = MediaTypeVideo(post, binding, inflater, listener)
                }
                PostType.RICH_VIDEO -> {
                    media = MediaTypeVideo(post, binding, inflater, listener)
                }
                PostType.TOURNAMENT -> {
                    binding.root.visibility = View.GONE
                }
                PostType.IMGUR_LINK -> {
                    data.urlOverriddenByDest?.let {
                        media = MediaTypeLink(post, binding, inflater, listener)
                    }
                }
                PostType.YOUTUBE -> {
                    media = MediaTypeYoutube(post, binding, inflater, listener)
                }
                PostType.NONE -> {
                }
                else -> {}
            }
            media?.show()

            data.selftext?.let {
                binding.redditMedia.redditDescriptionLayout.redditTextContent.visibility =
                    View.VISIBLE

                binding.redditMedia.redditDescriptionLayout.redditTextContent.movementMethod =
                    LinkMovementMethod.getInstance()
                binding.redditMedia.redditDescriptionLayout.redditTextContent.setText(
                    GetFormattedTextUseCase(listener).execute(it),
                    TextView.BufferType.SPANNABLE
                )
            }
        }
    }

    private fun setUpBottom() {
        CustomPostBottomSectionView(binding, post, listener).init()
    }

    private fun setUpDebug(post: RedditPost) {
        binding.bottomLayout.debug.setOnClickListener { Log.i("DEBUG", "$post") }
        binding.bottomLayout.debug.visibility = View.GONE
    }

    private fun clearViewsToDefaultValues() {
        binding.root.visibility = View.VISIBLE
        // Images
//        binding.redditMedia.galleryLayout.root.visibility = View.GONE
//        binding.redditMedia.galleryLayout.redditGalleryCount.visibility = View.GONE
//        binding.redditMedia.redditImageLayout.redditImage.visibility = View.GONE
//        // Web
//        binding.redditMedia.redditWebLayout.redditWebview.visibility = View.GONE
//        binding.redditMedia.redditWebLayout.redditWebview.loadUrl("about:blank")
//        // Link
//        binding.redditMedia.redditLinkLayout.redditLinkPreview.visibility = View.GONE
//        binding.redditMedia.redditLinkLayout.redditLinkSrc.visibility = View.GONE
//        binding.redditMedia.redditLinkLayout.redditLinkDescription.visibility = View.GONE
//        // Description
//        binding.redditMedia.redditDescriptionLayout.redditTextContent.visibility = View.GONE
//        // Video
//        binding.redditMedia.redditCustomPlayer.customPlayer.visibility = View.GONE
//        binding.redditMedia.redditCustomPlayer.customPlayerControllers.visibility = View.GONE
//        // Alternative video
//        binding.redditMedia.redditVideoPlayer.redditVideoPlayerLayout.visibility = View.GONE
//        // Youtube
//        binding.redditMedia.redditYoutubeLayout.layoutHolder.visibility = View.GONE
    }

    fun postInFocus(focus: Boolean) {
        media?.postInFocus(focus)
    }
}
