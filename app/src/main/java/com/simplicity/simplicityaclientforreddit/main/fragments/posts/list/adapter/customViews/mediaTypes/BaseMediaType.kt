package com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.mediaTypes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.RedditPostBinding
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.listeners.OldRedditPostListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.media.GetGalleryImageUrlUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaBaseValues
import com.simplicity.simplicityaclientforreddit.main.usecases.media.MediaData
import com.squareup.picasso.Picasso

open class BaseMediaType(var post: RedditPost, var binding: RedditPostBinding, val layoutInflater: LayoutInflater, val listener: OldRedditPostListener) {
    init { }
    open fun show() {}

    internal fun loadImageOrGif(urlForPicture: MediaData?, context: Context, view: ImageView) {
        urlForPicture?.let {
            if (it.mediaUrl.contains(".gif")) {
                loadGif(it.mediaUrl, context, view)
            } else {
                loadImage(it, view)
            }
        }
    }
    internal fun preLoadImageOrGif(mediaData: MediaData?, context: Context, view: ImageView) {
        mediaData?.let {
            if (it.mediaUrl.contains(".gif")) {
                loadGif(it.mediaUrl, context, view)
            } else {
                loadImage(mediaData, view)
            }
        }
    }

    private fun showImage(data: RedditPost.Data, context: Context, view: ImageView) {
        data.urlOverriddenByDest?.let {
            if (it.contains(".gif")) {
                loadGif(it, context, view)
            } else {
                data.preview?.images?.first()?.source?.let { source ->
                    loadImage(MediaData(it, GetGalleryImageUrlUseCase().getRatio(source.height, source.width), null, MediaBaseValues(source.height, source.width)), view)
                }
            }
        }
    }

    internal fun loadGif(it: String, context: Context, view: ImageView) {
        view.visibility = View.VISIBLE
        Glide
            .with(context)
            .load(it)
            .error(R.drawable.error_download) // show error drawable if the image is not a gif
            .into(view)
    }

    internal fun loadImage(it: MediaData, view: ImageView) {
//        view.layoutParams.height = GetMediaHeightUseCase(post.data, it).execute().mediaHeight
        Log.i("BaseMediaType", "Loading image ${it.mediaUrl}")
        view.requestLayout()
        view.visibility = View.VISIBLE
        val picasso = Picasso.get()
        Log.i("BaseMediaType", "Is it slow picasso instantiation?")
        val width = SettingsSP().loadSetting(SettingsSP.KEY_DEVICE_WIDTH, 0)
        if (width > 0) {
            picasso
                .load(it.mediaUrl)
                .resize(width, 0)
                .into(view)
        } else {
            picasso
                .load(it.mediaUrl)
                .resize(800, 0)
                .into(view)
        }
    }

    open fun postInFocus(focus: Boolean) {
    }
}
