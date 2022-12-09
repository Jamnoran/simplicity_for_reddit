package com.simplicity.simplicityaclientforreddit.main.screen.authenticate.result

import androidx.navigation.NavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput

class AuthenticationResultInput(var navController: NavController) : BaseInput

data class Data(val data: String) {
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}
