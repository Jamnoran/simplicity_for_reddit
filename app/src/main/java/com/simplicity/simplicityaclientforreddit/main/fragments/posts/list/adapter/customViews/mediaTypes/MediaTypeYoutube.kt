package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes

import android.util.Log
import android.view.LayoutInflater
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.simplicity.simplicityaclientforreddit.databinding.MediaYoutubeLayoutBinding
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.listeners.OldRedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostYoutubeIDUseCase

class MediaTypeYoutube(
    data: RedditPost,
    binding: RedditPostBinding,
    layoutInflater: LayoutInflater,
    listener: OldRedditPostListener
) : BaseMediaType(data, binding, layoutInflater, listener) {
    var player: YouTubePlayer? = null
    var mediaYoutubeLayoutBinding: MediaYoutubeLayoutBinding? = null
    override fun show() {
        post.data.urlOverriddenByDest?.let {
            mediaYoutubeLayoutBinding = MediaYoutubeLayoutBinding.inflate(layoutInflater)
            mediaYoutubeLayoutBinding?.let {
                binding.redditMedia.redditMediaHolder.addView(it.root)
                val youTubePlayerView = it.youtubePlayerView
//            lifecycle.addObserver(youTubePlayerView)

                youTubePlayerView.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            player = youTubePlayer
                            val videoId = GetPostYoutubeIDUseCase().execute(post.data)
                            videoId?.let {
                                youTubePlayer.cueVideo(it, 0f)
                            } ?: run {
                                Log.i("MediaTypeYoutube", "Could not get a hold of youtube id ")
                            }
                        }
                    })
            }
        }
    }

    override fun postInFocus(focus: Boolean) {
        super.postInFocus(focus)
        player?.let {
            if (focus) {
                it.play()
            } else {
                it.pause()
            }
        }
        mediaYoutubeLayoutBinding?.focusText?.text =
            "Youtube id : ${GetPostYoutubeIDUseCase().execute(post.data)}"
    }
}
