package com.simplicity.simplicityaclientforreddit.main.components.buttons

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.R
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

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun CToggleButton(
    isChecked: Boolean,
    onClick: () -> Unit,
    disabledIcon: Int,
    enabledIcon: Int
) {
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = { onClick() }
    ) {
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val tint by transition.animateColor(
            label = "Tint"
        ) { isChecked ->
            if (isChecked) Color.Red else Color.Black
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                        40.dp at 75 // ms
                        35.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 30.dp }
        Image(
            modifier = Modifier
                .size(24.dp),
            contentDescription = "",
            painter = painterResource(if (isChecked) enabledIcon else disabledIcon)
        )
    }
}

@Composable
fun TextButton(modifier: Modifier = Modifier, text: String, click: () -> Unit) {
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
