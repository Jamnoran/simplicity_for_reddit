package com.simplicity.simplicityaclientforreddit.main.utils.extensions // ktlint-disable filename

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.arg(keyName: String): String {
    return arguments?.getString(keyName) ?: ""
}
