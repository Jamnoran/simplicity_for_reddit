package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import com.simplicity.simplicityaclientforreddit.databinding.MediaCustomPlayerBinding
import com.simplicity.simplicityaclientforreddit.main.Global
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
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
    val audioPlayer = AudioPlayer(post)

    var hasPreparedVideo = false
    val mediaData = GetMediaDataUseCase().execute(post.data)
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.px
    val videoHeight = getVideoHeight(mediaData, screenWidth)

    var canShowVideo = true
    var isPlaying = true

    val startVideo: () -> Unit = {
        Log.i("PostVideo", "Post is hidden, lets resume audio")
        audioPlayer.start()
        try {
            videoMediaPlayer?.start()
        } catch (e: Exception) {
            Log.i("PostVideo", "Could not start video again :(")
        }
        isPlaying = true
    }
    val pauseVideo: () -> Unit = {
        Log.i("PostVideo", "Post is hidden, lets pause audio")
        audioPlayer.pause()
        videoMediaPlayer?.pause()
        isPlaying = false
    }
    val checkIfAllIsPreparedAndStartPlaying: () -> Unit = {
        Log.i(
            "PostVideo",
            "checkIfAllIsPreparedAndStartPlaying : ${hasPreparedVideo && audioPlayer.hasPreparedAudio()}"
        )
        if (hasPreparedVideo && audioPlayer.hasPreparedAudio()) {
            startVideo.invoke()
        }
    }

    Log.i("PostVideo", "videoParams width:$screenWidth height: $videoHeight")
    val uri = Uri.parse(mediaData.mediaUrl)
    Column(Modifier.fillMaxWidth()) {
        val interactionSource = remember { MutableInteractionSource() }
        Box(
            Modifier.fillMaxWidth().clickable(
                interactionSource = interactionSource,
                indication = null
            ) { if (isPlaying) pauseVideo.invoke() else startVideo.invoke() }
        ) {
            AndroidViewBinding(
                MediaCustomPlayerBinding::inflate
            ) {
                listener.postHiddenFromView = {
                    pauseVideo.invoke()
                }
                listener.postShownFromView = {
                    audioPlayer.restart()
                }

                customPlayer.setVideoURI(uri)
                customPlayer.setOnErrorListener { _, what, extra ->
                    Log.i(
                        "PostVideo",
                        "An Error Occurred " +
                            "While Playing Video !!!, What: $what  ---  Extra: $extra"
                    )
//                    listener.showError("Could not play video")
                    canShowVideo = false
                    customPlayer.visibility = View.GONE
                    false
                }
                customPlayer.layoutParams = LayoutParams(screenWidth, videoHeight.toInt())
                customPlayer.setOnCompletionListener {
                    Log.i("PostVideo", "Player has reached its end")
                    it.seekTo(0)
                    it.start()
                    audioPlayer.restart()
                }
                customPlayer.setOnPreparedListener {
//                    it.isLooping = true
                    videoMediaPlayer = it
                    hasPreparedVideo = true
                    checkIfAllIsPreparedAndStartPlaying.invoke()
                }

                audioPlayer.checkIfVideoHasAudio(Global.applicationContext) {
                    checkIfAllIsPreparedAndStartPlaying.invoke()
                }
            }
            if (!canShowVideo) {
                CText("Not possible to show video :(")
            }
        }
    }
}

private fun getVideoHeight(mediaData: MediaData, screenWidth: Int): Float {
    return if (mediaData.baseValues.mediaHeight < mediaData.baseValues.mediaWidth) {
        val ratio: Float = screenWidth / mediaData.baseValues.mediaWidth.toFloat()
        ratio * mediaData.baseValues.mediaHeight
    } else {
        val ratio: Float =
            mediaData.baseValues.mediaHeight.toFloat() / mediaData.baseValues.mediaWidth.toFloat()
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
