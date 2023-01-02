package com.simplicity.simplicityaclientforreddit.main.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CRoundedCorners(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.clip(shape = RoundedCornerShape(4.dp))
            .background(color = DarkGrayTransparent)
    ) {
        content.invoke()
    }
}

class Shape {
    companion object {
        val BOTTOM_NAV_HEIGHT = 100.dp
    }
}
