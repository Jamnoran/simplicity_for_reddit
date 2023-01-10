package com.simplicity.simplicityaclientforreddit.main.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.theme.Background

@Composable
fun ScreenEmpty(emptyMessage: String? = null) {
    Column {
    }
}

@Preview
@Composable
fun PreviewEmpty() {
    Column(Modifier.fillMaxWidth().fillMaxHeight().background(Background)) {
        ScreenEmpty("Empty")
    }
}
