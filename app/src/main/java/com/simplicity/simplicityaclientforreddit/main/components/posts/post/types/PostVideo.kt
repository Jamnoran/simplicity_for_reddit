package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import com.simplicity.simplicityaclientforreddit.databinding.MediaCustomPlayerBinding
import com.simplicity.simplicityaclientforreddit.main.Global
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaDataUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaData
import com.simplicity.simplicityaclientforreddit.main.utils.extensions.px

@Composable
fun PostVideo(post: RedditPost, listener: RedditPostListener) {
    ShowVideo(post, listener)
}

@Composable
fun ShowVideo(post: RedditPost, listener: RedditPostListener) {
    var videoMediaPlayer: MediaPlayer? = null
    var hasPreparedVideo = false
    val mediaData = GetMediaDataUseCase().execute(post.data)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.px
    val videoHeight = getVideoHeight(mediaData, screenWidth)
    Log.i("PostVideo", "videoParams width:$screenWidth height: $videoHeight")
    val uri = Uri.parse(mediaData.mediaUrl)
    Column(Modifier.fillMaxWidth()) {
        Box(Modifier.fillMaxWidth()) {
            AndroidViewBinding(
                MediaCustomPlayerBinding::inflate
            ) {
                val audioPlayer = AudioPlayer(post)
                listener.postHiddenFromView = {
                    Log.i("PostVideo", "Post is hidden, lets pause music")
                    audioPlayer.pause()
                    videoMediaPlayer?.pause()
                }

                customPlayer.setVideoURI(uri)
                customPlayer.setOnErrorListener { mp, what, extra ->
                    Log.i(
                        "PostVideo",
                        "An Error Occurred " +
                            "While Playing Video !!!, What: $what  ---  Extra: $extra"
                    )
                    listener.showError("Could not play video")
                    false
                }
                customPlayer.layoutParams = LayoutParams(screenWidth, videoHeight.toInt())
//                customPlayer.setOnCompletionListener {
//                    it.seekTo(0)
//                    it.start()
//                }
                customPlayer.setOnPreparedListener {
                    it.isLooping = true
                    videoMediaPlayer = it
                    hasPreparedVideo = true
                    customPlayer.start()
                }

                audioPlayer.checkIfVideoHasAudio(Global.applicationContext)
            }
        }
    }
}

private fun getVideoHeight(mediaData: MediaData, screenWidth: Int): Float {
    return if (mediaData.baseValues.mediaHeight < mediaData.baseValues.mediaWidth) {
        val ratio: Float = screenWidth / mediaData.baseValues.mediaWidth.toFloat()
        ratio * mediaData.baseValues.mediaHeight
    } else {
        val ratio: Float = mediaData.baseValues.mediaHeight.toFloat() / mediaData.baseValues.mediaWidth.toFloat()
        ratio.times(screenWidth)
    }
}

@Preview
@Composable
fun PreviewPostVideo() {
    Column() {
        PostVideo(post = TesterHelper.getPost(), listener = RedditPostListener.preview())
    }
}
