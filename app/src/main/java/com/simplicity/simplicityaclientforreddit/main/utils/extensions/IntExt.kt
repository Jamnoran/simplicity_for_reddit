package com.simplicity.simplicityaclientforreddit.main.utils.extensions

import android.content.res.Resources

val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
