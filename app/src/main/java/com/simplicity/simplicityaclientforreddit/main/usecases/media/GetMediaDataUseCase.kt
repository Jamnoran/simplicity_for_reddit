package com.simplicity.simplicityaclientforreddit.main.usecases.media

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.media.VideoHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class GetMediaDataUseCase {
    fun execute(data: RedditPost.Data): MediaData {
        var mediaData = MediaData("", null, null, MediaBaseValues(0, 0))
        data.preview?.reddit_video_preview?.fallback_url?.let {
            mediaData = MediaData(it, null, VideoHelper.getAudioUrl(it), MediaBaseValues(data.preview?.reddit_video_preview?.width!!, data.preview?.reddit_video_preview?.height!!))
            Log.i(TAG, "Setting video from reddit_video_preview $it")
        }
        data.media?.reddit_video?.fallback_url?.let {
            if (mediaData.mediaUrl.isEmpty()) {
                mediaData = MediaData(it, null, VideoHelper.getAudioUrl(it), MediaBaseValues(data.media?.reddit_video?.width!!, data.media?.reddit_video?.height!!))
                Log.i(TAG, "Setting video from reddit_video $it")
            }
        }
        data.secureMediaEmbed?.media_domain_url?.let {
            if (mediaData.mediaUrl.isEmpty()) {
                mediaData = MediaData(it, null, VideoHelper.getAudioUrl(it), MediaBaseValues(data.secureMediaEmbed?.width!!, data.secureMediaEmbed?.height!!))
                Log.i(TAG, "Setting video from secureMediaEmbed $it")
            }
        }
        return mediaData
    }

    companion object {
        private const val TAG = "GetMediaDataUseCase"
    }
}
