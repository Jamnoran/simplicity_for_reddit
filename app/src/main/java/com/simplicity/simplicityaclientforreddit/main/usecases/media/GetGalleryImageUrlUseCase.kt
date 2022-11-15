package com.simplicity.simplicityaclientforreddit.main.usecases.media

import com.simplicity.simplicityaclientforreddit.main.media.ImageUtil.Companion.getConvertedImageUrl
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

class GetGalleryImageUrlUseCase {
    fun execute(data: RedditPost.Data, positionToGetImage: Int): GalleryItem {
        val galleryItem = GalleryItem(null, null, null)
        data.galleryData?.items?.let { items ->
            for ((i, item) in items.withIndex()) {
                val previousPosition = positionToGetImage - 1
                val nextPosition = positionToGetImage + 1

                // Get previous image
                if (i > 0 && i == previousPosition) {
                    galleryItem.previous = getMediaData(data, item.media_id)
                }
                // Get current image
                if (i == positionToGetImage) {
                    galleryItem.current = getMediaData(data, item.media_id)
                }
                // Get next image
                if (nextPosition < items.size && i == nextPosition) {
                    galleryItem.next = getMediaData(data, item.media_id)
                }
            }
        }
        return galleryItem
    }

    private fun getMediaData(data: RedditPost.Data, mediaId: String): MediaData? {
        data.mediaMetadata?.let { mediaMetaData ->
            val mediaData = mediaMetaData[mediaId]
            mediaData?.let {
                if (it.s.gif != null) { return MediaData(getConvertedImageUrl(it.s.gif), getRatio(it.s.x, it.s.y), null, MediaBaseValues(it.s.x, it.s.y)) }
                if (it.s.u != null) { return MediaData(getConvertedImageUrl(it.s.u), getRatio(it.s.x, it.s.y), null, MediaBaseValues(it.s.x, it.s.y)) }

                if (it.p.isNotEmpty()) { return MediaData(getConvertedImageUrl(it.p.first().u), getRatio(it.p.first().x, it.p.first().y), null, MediaBaseValues(it.p.first().x, it.p.first().y)) }
            }
        }
        return null
    }

    fun getRatio(y: Int, x: Int): Float? {
        return y.toFloat() / x.toFloat()
    }
}
class GalleryItem(var previous: MediaData?, var current: MediaData?, var next: MediaData?)
class MediaData(var mediaUrl: String, var imageRatio: Float?, var audioUrl: String? = null, var baseValues: MediaBaseValues)
class MediaBaseValues(var mediaWidth: Int, var mediaHeight: Int)
