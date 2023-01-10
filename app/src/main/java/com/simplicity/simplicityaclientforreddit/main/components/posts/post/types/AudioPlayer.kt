package com.simplicity.simplicityaclientforreddit.main.components.posts.post.types

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetMediaDataUseCase
import java.io.IOException

class AudioPlayer(val post: RedditPost) {

    private var audioMediaPlayer: MediaPlayer? = null
    private var preparedAudio = false

    private fun initAudio(audioPrepared: () -> Unit) {
        // Check that we have not started a new video before this tries to play
        val postMediaData = GetMediaDataUseCase().execute(post.data)

        audioMediaPlayer = MediaPlayer()
        audioMediaPlayer?.let { mediaPlayer ->
            // below line is use to set the audio
            // stream type for our media player.
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            // below line is use to set our
            // url to our media player.
            try {
                mediaPlayer.setDataSource(postMediaData.audioUrl)
                // below line is use to prepare
                // and start our media player.
                mediaPlayer.prepare()
                mediaPlayer.setOnPreparedListener {
                    Log.i(TAG, "Has prepared audio")
                    audioMediaPlayer = it
                    preparedAudio = true
                    audioPrepared.invoke()
                }
            } catch (e: IOException) {
                Log.e(TAG, "Could not play audio$e")
                //            videoPlayer.play();
                preparedAudio = true
                audioPrepared.invoke()
            }
        }
    }

    fun checkIfVideoHasAudio(context: Context, audioPrepared: () -> Unit) {
        val postMediaData = GetMediaDataUseCase().execute(post.data)
        val queue = Volley.newRequestQueue(context)
        // Request a string response from the provided URL.
        val stringRequest =
            StringRequest(Request.Method.GET, postMediaData.audioUrl, { response: String? ->
                Log.i(TAG, "Volley request success")
                initAudio(audioPrepared)
            }) { error: VolleyError? ->
                Log.i(TAG, "Volley request failed $error")
                preparedAudio = true
                audioPrepared.invoke()
            }

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun pause() {
        audioMediaPlayer?.let {
            it.pause()
            Log.i(TAG, "Pausing audio")
        }
    }

    fun start() {
        audioMediaPlayer?.let {
            it.start()
            Log.i(TAG, "Resuming audio")
        }
    }

    fun hasPreparedAudio(): Boolean {
        return preparedAudio
    }

    fun restart() {
        audioMediaPlayer?.let {
            it.seekTo(0)
            it.start()
        }
    }

    companion object {
        const val TAG = "AudioPlayer"
    }
}
