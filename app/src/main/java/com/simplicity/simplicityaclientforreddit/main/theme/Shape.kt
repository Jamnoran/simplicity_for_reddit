package com.simplicity.simplicityaclientforreddit.main.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText

@Composable
fun CRoundedCorners(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.clip(shape = RoundedCornerShape(4.dp))
            .background(color = DarkGrayTransparent)
    ) {
        content.invoke()
    }
}

@Composable
fun CRoundedCorners() = RoundedCornerShape(16.dp)

@Composable
fun CRoundedBottomCorners() = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)

class Shape {
    companion object {
        val BOTTOM_NAV_HEIGHT = 100.dp
    }
}

@Preview()
@Composable
fun Preview() {
    Column(modifier = Modifier.padding(12.dp).background(Surface)) {
        Box(
            modifier = Modifier.clip(shape = CRoundedCorners())
                .background(color = DarkGrayTransparent)
        ) {
            CText(modifier = Modifier.padding(8.dp), text = "Simple text")
        }
        Box(
            modifier = Modifier.clip(shape = CRoundedBottomCorners())
                .background(color = DarkGrayTransparent)
        ) {
            CText(modifier = Modifier.padding(8.dp), text = "Simple text")
        }
    }
}
