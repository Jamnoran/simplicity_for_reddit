package com.simplicity.simplicityaclientforreddit.main.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconToggleButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText

@Composable
fun CButton(
    icon: Int,
    clickedIcon: Int,
    click: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    val iconResource by remember {
        if (clicked) {
            mutableStateOf(clickedIcon)
        } else {
            mutableStateOf(icon)
        }
    }
    IconToggleButton(checked = clicked, onCheckedChange = {
        clicked = !clicked
    }) {
        Image(
            modifier = Modifier
                .padding(4.dp)
                .size(24.dp)
                .clickable {
                    click.invoke()
                },
            contentDescription = "",
            painter = painterResource(iconResource)
        )
    }
}

@Composable
fun CToggleButton(
    isChecked: Boolean,
    onClick: () -> Unit,
    disabledIcon: Int,
    enabledIcon: Int
) {
    var toggled by remember { mutableStateOf(isChecked) }
    TextButton(
        modifier = Modifier.background(Color.Transparent),
        onClick = {
            toggled = !toggled
            onClick.invoke()
        }
    ) {
        CImage(
            iconResource = if (toggled) enabledIcon else disabledIcon
        )
    }
}

@Composable
fun CTextButton(modifier: Modifier = Modifier, text: String, click: () -> Unit) {
    CText(modifier.clickable { click.invoke() }, text = text)
}

@Preview
@Composable
fun Preview() {
    Row(Modifier.fillMaxWidth()) {
        CButton(
            icon = R.drawable.down_arrow_disabled,
            clickedIcon = R.drawable.down_arrow_clicked
        ) { }
        CButton(icon = R.drawable.up_arrow_disabled, clickedIcon = R.drawable.up_arrow_clicked) { }
        val (isChecked, setChecked) = remember { mutableStateOf(false) }
        CToggleButton(
            isChecked = isChecked,
            disabledIcon = R.drawable.down_arrow_disabled,
            enabledIcon = R.drawable.down_arrow_clicked,
            onClick = { setChecked(!isChecked) }
        )
    }
}
