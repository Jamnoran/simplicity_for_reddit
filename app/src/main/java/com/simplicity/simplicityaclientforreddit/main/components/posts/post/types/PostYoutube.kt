package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostYoutubeIDUseCase

@Composable
fun PostYoutube(post: RedditPost, listener: RedditPostListener) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context)
        },
        update = { youtubePlayerView ->
            youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = GetPostYoutubeIDUseCase().execute(post.data)
                        videoId?.let { videoId ->
                            youTubePlayer.cueVideo(videoId, 0f)
                        } ?: run {
                            Log.i("MediaTypeYoutube", "Could not get a hold of youtube id ")
                        }
                    }
                })
        },
        modifier = Modifier
    )
}
