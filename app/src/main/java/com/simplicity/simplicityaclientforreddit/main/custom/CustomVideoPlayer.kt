package com.simplicity.simplicityaclientforreddit.main.custom

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.PositionInfo
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.text.Cue
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.video.VideoSize

class CustomVideoPlayer(
    private val videoSurfaceView: PlayerView,
    val eventListener: CustomPlayerEventListener
) {

    private var videoPlayer: ExoPlayer? = null

    fun init(context: Context) {
        videoSurfaceView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

        // 2. Create the player
        videoPlayer = ExoPlayer.Builder(context).build()

        // Bind the player to the view.
        videoSurfaceView.useController = false
        videoSurfaceView.player = videoPlayer

        setUpListener()
    }

    private fun setUpListener() {
        videoPlayer?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                super.onTimelineChanged(timeline, reason)
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
            }

//            override fun onTracksChanged(
//                trackGroups: TrackGroupArray,
//                trackSelections: TrackSelectionArray
//            ) {
//                super.onTracksChanged(trackGroups, trackSelections)
//            }

//            override fun onTracksInfoChanged(tracksInfo: TracksInfo) {
//                super.onTracksInfoChanged(tracksInfo)
//            }

            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                super.onMediaMetadataChanged(mediaMetadata)
            }

            override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
                super.onPlaylistMetadataChanged(mediaMetadata)
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                super.onLoadingChanged(isLoading)
            }

            override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
                super.onAvailableCommandsChanged(availableCommands)
            }

            override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
                super.onTrackSelectionParametersChanged(parameters)
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        Log.e(Companion.TAG, "onPlayerStateChanged: Buffering video.")
                        eventListener.showProgressBar()
//                        if (progressBar != null) {
//                            progressBar.setVisibility(View.VISIBLE)
//                        }
                    }
                    Player.STATE_ENDED -> {
                        Log.d(TAG, "onPlayerStateChanged: Video ended.")
                        videoPlayer?.seekTo(0)
                        eventListener.resetAudio()
//                        if (audioMediaPlayer != null) {
//                            audioMediaPlayer.seekTo(0)
//                        }
                    }
                    Player.STATE_IDLE -> {}
                    Player.STATE_READY -> {
                        Log.e(TAG, "onPlayerStateChanged: Ready to play.")
//                        if (progressBar != null) {
//                            progressBar.setVisibility(View.GONE)
//                        }
                        eventListener.hideProgressBar()
                        eventListener.playerStateReady()
//                        if (!isVideoViewAdded) {
//                            addVideoView()
//                        }
                    }
                    else -> {}
                }
            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
            }

            override fun onPlaybackSuppressionReasonChanged(playbackSuppressionReason: Int) {
                super.onPlaybackSuppressionReasonChanged(playbackSuppressionReason)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                Log.i(TAG, "onIsPlayingChanged $isPlaying")
//                if (audioMediaPlayer != null) {
                if (isPlaying) {
                    eventListener.playAudio()
//                        audioMediaPlayer.start()
                } else {
                    eventListener.pauseAudio()
//                        audioMediaPlayer.stop()
//                    }
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                super.onRepeatModeChanged(repeatMode)
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                super.onShuffleModeEnabledChanged(shuffleModeEnabled)
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
            }

            override fun onPlayerErrorChanged(error: PlaybackException?) {
                super.onPlayerErrorChanged(error)
            }

            override fun onPositionDiscontinuity(reason: Int) {
                super.onPositionDiscontinuity(reason)
            }

            override fun onPositionDiscontinuity(
                oldPosition: PositionInfo,
                newPosition: PositionInfo,
                reason: Int
            ) {
                super.onPositionDiscontinuity(oldPosition, newPosition, reason)
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
                super.onPlaybackParametersChanged(playbackParameters)
            }

            override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
                super.onSeekBackIncrementChanged(seekBackIncrementMs)
            }

            override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
                super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
            }

            override fun onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs: Long) {
                super.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs)
            }

            override fun onSeekProcessed() {
                super.onSeekProcessed()
            }

            override fun onAudioSessionIdChanged(audioSessionId: Int) {
                super.onAudioSessionIdChanged(audioSessionId)
            }

            override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
                super.onAudioAttributesChanged(audioAttributes)
            }

            override fun onVolumeChanged(volume: Float) {
                super.onVolumeChanged(volume)
            }

            override fun onSkipSilenceEnabledChanged(skipSilenceEnabled: Boolean) {
                super.onSkipSilenceEnabledChanged(skipSilenceEnabled)
            }

            override fun onDeviceInfoChanged(deviceInfo: DeviceInfo) {
                super.onDeviceInfoChanged(deviceInfo)
            }

            override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
                super.onDeviceVolumeChanged(volume, muted)
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                super.onVideoSizeChanged(videoSize)
            }

            override fun onSurfaceSizeChanged(width: Int, height: Int) {
                super.onSurfaceSizeChanged(width, height)
            }

            override fun onRenderedFirstFrame() {
                super.onRenderedFirstFrame()
            }

            override fun onCues(cues: List<Cue>) {
                super.onCues(cues)
            }

            override fun onMetadata(metadata: Metadata) {
                super.onMetadata(metadata)
            }
        })
    }

    fun setVolume(volume: Float) {
        videoPlayer?.volume = volume
    }

    fun play() {
        videoPlayer?.play()
    }

    fun pause() {
        videoPlayer?.pause()
    }

    fun clearVideo() {
        removeVideoView(videoSurfaceView)
    }

    // Remove the old player
    private fun removeVideoView(videoView: PlayerView) {
        videoView.parent?.let {
            val parent = videoView.parent as ViewGroup ?: return
            val index = parent.indexOfChild(videoView)
            if (index >= 0) {
                parent.removeViewAt(index)
                //            isVideoViewAdded = false
                //            viewHolderParent.setOnClickListener(null)
            }
        } ?: Log.w(TAG, "Failed to remove video view")
    }

    fun playMediaItem(mediaItem: MediaItem) {
        videoPlayer?.setMediaItem(mediaItem)
        videoPlayer?.prepare()

        videoPlayer?.playWhenReady = true
    }

    fun getSurfaceView(): PlayerView {
        return videoSurfaceView
    }

    companion object {
        private val TAG = "CustomVideoPlayer"
    }
}

interface CustomPlayerEventListener {
    fun showProgressBar()
    fun hideProgressBar()
    fun resetAudio()
    fun playerStateReady()
    fun playAudio()
    fun pauseAudio()
}
