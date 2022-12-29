package com.simplicity.simplicityaclientforreddit.main.screen.menu

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput

class MenuInput(val inputData: String = "") : BaseInput

data class Data(val data: String) {
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}
