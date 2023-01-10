package com.simplicity.simplicityaclientforreddit.main.screen.splash

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class SplashInput(val inputData: String = "", var navigationListener: NavigationListener? = null) : BaseInput

data class Data(val data: String) {
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}
