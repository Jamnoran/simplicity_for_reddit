package com.simplicity.simplicityaclientforreddit.main.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground

@Composable
fun Loading(loadingMessage: String? = null) {
    DefaultScreen(modifier = Modifier) {
        Column {
            Spacer(modifier = Modifier.height(400.dp))
            CText(
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(),
                text = loadingMessage ?: "Loading",
                color = OnBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoading() {
    Column(Modifier.fillMaxWidth().fillMaxHeight().background(Background)) {
        Loading("Loading message")
    }
}
