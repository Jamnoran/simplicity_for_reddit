package com.simplicity.simplicityaclientforreddit.main.media

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.MediaCustomPlayerBinding
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaBaseValuesUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaDataUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaData
import java.io.IOException

class CustomPlayer(
    var binding: MediaCustomPlayerBinding,
    var data: RedditPost.Data,
    var context: Context
) {
    private val TAG = "CustomPlayer"
    private lateinit var videoMediaPlayer: MediaPlayer
    private var audioMediaPlayer: MediaPlayer? = null
    private var mediaData: MediaData? = null

    //    private var videoUrl: String? = null
//    private lateinit var audioUrl: String
    private var hasAudio: Boolean? = null
    private var muted = false
    private var hasPreparedVideo = false
    private var hasPreparedAudio = false
    private var videoHeight: Int = 0
    private var videoWidth: Int = 0

    fun init() {
        parseUrls()
        binding.playButton.visibility = View.GONE
        binding.thumbnail.visibility = View.VISIBLE
        setUpListeners()
        checkIfVideoHasAudio()
        // initializing video player
        initVideo()
    }

    fun getVideoParams(): ConstraintLayout.LayoutParams {
        mediaData?.let {
            val mediaBaseValues = GetMediaBaseValuesUseCase(data, it).execute()
            return ConstraintLayout.LayoutParams(
                mediaBaseValues.mediaWidth,
                mediaBaseValues.mediaHeight
            )
        }
        return ConstraintLayout.LayoutParams(
            0,
            0
        )
    }

    fun getVideoHeight(): Int? {
        return mediaData?.let {
            val mediaBaseValues = GetMediaBaseValuesUseCase(data, it).execute()
            return mediaBaseValues.mediaHeight
        }
    }

    private fun checkIfVideoHasAudio() {
        val queue = Volley.newRequestQueue(binding.root.context)
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET,
            mediaData?.audioUrl,
            {
                Log.i(TAG, "Volley request success")
                hasAudio = true
                // initializing media player
                initAudio()
                tryPlayVideo()
            },
            {
                Log.i(TAG, "Volley request failed")
                hasAudio = false
                hasPreparedAudio = true
                binding.muteButton.visibility = View.GONE
                tryPlayVideo()
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun tryPlayVideo() {
        binding.thumbnail.visibility = View.GONE
        if (hasPreparedVideo) {
            if (hasAudio == true) {
                binding.customPlayer.start()
                audioMediaPlayer?.start()
            } else {
                Log.i(TAG, "Video does not have audio")
                binding.customPlayer.start()
            }
        }
    }

    private fun muteAudio() {
        muted = true
        binding.muteButton.setImageResource(R.drawable.ic_mute)
        audioMediaPlayer?.setVolume(0.0f, 0.0f)
    }

    private fun unMuteAudio() {
        muted = false
        binding.muteButton.setImageResource(R.drawable.ic_audio)
        audioMediaPlayer?.setVolume(1.0f, 1.0f)
    }

    private fun setUpListeners() {
//        binding.customPlayerControllers.setOnClickListener {
//            pause()
//        }
        binding.playButton.setOnClickListener {
            play()
        }
        binding.muteButton.setOnClickListener {
            if (muted) {
                unMuteAudio()
            } else {
                muteAudio()
            }
        }
        binding.restartButton.setOnClickListener {
            restartVideo()
        }
    }

    private fun restartVideo() {
        videoMediaPlayer.seekTo(0)
//        audioMediaPlayer?.seekTo(0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) audioMediaPlayer?.seekTo(
            0,
            MediaPlayer.SEEK_CLOSEST
        ) else audioMediaPlayer?.seekTo(0)
    }

    private fun parseUrls() {
        mediaData = GetMediaDataUseCase().execute(data)
    }

    private fun initVideo() {
        // specify the location of media file
        val uri = Uri.parse(mediaData?.mediaUrl)
        Log.i(TAG, "We got this url for video: $uri")
        // Setting MediaController and URI, then starting the videoView
        binding.customPlayer.setVideoURI(uri)
        binding.customPlayer.requestFocus()
        binding.customPlayer.setOnPreparedListener {
            videoMediaPlayer = it
            hasPreparedVideo = true
            tryPlayVideo()
        }
        binding.customPlayer.setOnCompletionListener {
//            it.start()
//            audioMediaPlayer?.start()

            binding.playButton.visibility = View.VISIBLE
        }
//        videoView.setOnInfoListener { mediaPlayer, what, extra ->
//            if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
//                Log.i(TAG, "Starting audio")
// //                mediaPlayer.start()
//            }
//            false
//        }
    }

    private fun initAudio() {
        // initializing media player
        audioMediaPlayer = MediaPlayer()

        // below line is use to set the audio
        // stream type for our media player.
        audioMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)

        // below line is use to set our
        // url to our media player.
        try {
            audioMediaPlayer?.setDataSource(mediaData?.audioUrl)
            // below line is use to prepare
            // and start our media player.
            audioMediaPlayer?.prepare()
            audioMediaPlayer?.setOnPreparedListener {
                Log.i(TAG, "Audio is prepared, starting video")
                hasPreparedAudio = true
//                unMuteAudio()
                tryPlayVideo()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        muteAudio()
    }

    private fun pause() {
        binding.playButton.visibility = View.VISIBLE
        videoMediaPlayer.pause()
        try {
            audioMediaPlayer?.pause()
        } catch (e: Exception) {
        }
    }

    private fun play() {
        binding.thumbnail.visibility = View.GONE
        binding.playButton.visibility = View.GONE
        videoMediaPlayer.start()
        try {
            audioMediaPlayer?.start()
        } catch (e: Exception) {
        }
    }
}
