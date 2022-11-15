package com.simplicity.simplicityaclientforreddit.main.listeners

import android.content.Intent

class NavigationListener(
    val navigate: (intent: Intent) -> Unit
) {
    companion object {
        fun preview(): NavigationListener {
            return NavigationListener {  }
        }
    }
}
