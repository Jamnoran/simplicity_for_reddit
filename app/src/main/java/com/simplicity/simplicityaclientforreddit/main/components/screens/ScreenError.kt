package com.simplicity.simplicityaclientforreddit.main.components.screens

import androidx.compose.runtime.Composable
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText

@Composable
fun ScreenError(errorMessage: String = "Error!") {
    CText(text = errorMessage)
}
