package com.simplicity.simplicityaclientforreddit.main.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnPrimaryText
import com.simplicity.simplicityaclientforreddit.main.theme.Background

@Composable
fun DefaultScreen(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier.fillMaxSize().background(Background)) {
        content.invoke()
    }
}

@Preview
@Composable
fun DefaultScreenPreview() {
    DefaultScreen(Modifier) {
        OnPrimaryText(text = "Test")
    }
}
