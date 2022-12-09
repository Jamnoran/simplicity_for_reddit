package com.simplicity.simplicityaclientforreddit.main.screen.authenticate

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class AuthenticateInput(val navigationListener: NavigationListener) : BaseLogic.BaseInput

data class Data(val data: String) {
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}
