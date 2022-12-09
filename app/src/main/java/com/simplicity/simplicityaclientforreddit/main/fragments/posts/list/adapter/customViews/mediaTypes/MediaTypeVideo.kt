package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.simplicity.simplicityaclientforreddit.databinding.MediaCustomPlayerBinding
import com.simplicity.simplicityaclientforreddit.databinding.MediaVideoPlayerBinding
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.listeners.OldRedditPostListener
import com.simplicity.simplicityaclientforreddit.main.media.CustomPlayer
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetGalleryImageUrlUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaBaseValuesUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaDataUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaBaseValues
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaData

class MediaTypeVideo(
    post: RedditPost,
    binding: RedditPostBinding,
    layoutInflater: LayoutInflater,
    listener: OldRedditPostListener
) : BaseMediaType(post, binding, layoutInflater, listener) {
    var mediaVideoPlayerBinding: MediaVideoPlayerBinding? = null
    override fun show() {
        mediaVideoPlayerBinding = MediaVideoPlayerBinding.inflate(layoutInflater)
        mediaVideoPlayerBinding?.let { mediaBinding ->
            val customVideoPlayer = MediaCustomPlayerBinding.inflate(layoutInflater)
            mediaBinding.mediaContainer.addView(customVideoPlayer.root) // Add custom player to reddit media holder
            binding.redditMedia.redditMediaHolder.addView(mediaBinding.root) // Add reddit media holder to main view
            customVideoPlayer.customPlayer.visibility = View.VISIBLE
            customVideoPlayer.customPlayerControllers.visibility = View.VISIBLE
            val player = CustomPlayer(customVideoPlayer, post.data, binding.root.context)
            player.init()
            customVideoPlayer.customPlayer.layoutParams = player.getVideoParams()
            listener.initVideoPlayer(reddit = post, mediaBinding)

            mediaBinding.redditVideoPlayerLayout.visibility = View.VISIBLE
            showThumbnail(customVideoPlayer)
        }
    }

    private fun showThumbnail(customVideoPlayer: MediaCustomPlayerBinding) {
        val postMediaData = GetMediaDataUseCase().execute(post.data)
        val mediaBaseValues = GetMediaBaseValuesUseCase(post.data, postMediaData).execute()
        val htmlString = post.data.preview!!.images.first().source.url
        val url = if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(htmlString).toString()
        }
        customVideoPlayer.thumbnail.scaleType = ImageView.ScaleType.FIT_XY
        customVideoPlayer.thumbnail.layoutParams.height = mediaBaseValues.mediaHeight
        loadImage(
            MediaData(
                url,
                GetGalleryImageUrlUseCase().getRatio(
                    mediaBaseValues.mediaHeight,
                    mediaBaseValues.mediaWidth
                ),
                null,
                MediaBaseValues(mediaBaseValues.mediaHeight, mediaBaseValues.mediaWidth)
            ),
            customVideoPlayer.thumbnail
        )
    }
}
