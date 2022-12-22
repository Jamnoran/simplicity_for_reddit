package com.simplicity.simplicityaclientforreddit.main.screen.posts.fullscreen.image

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput

class FullScreenImageInput(val inputData: String = "") : BaseInput

data class Data(val data: String) {
    companion object {
        fun preview(): Data {
            return Data("Preview")
        }
    }
}
