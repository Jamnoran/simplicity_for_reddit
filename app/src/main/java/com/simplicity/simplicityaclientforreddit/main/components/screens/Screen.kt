package com.simplicity.simplicityaclientforreddit.main.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnPrimaryText
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.BackgroundVariant

@Composable
fun DefaultScreen(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart?.let { it() }
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop?.let { it() }
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Box(modifier = modifier.fillMaxSize().background(getGradiantBackground())) {
        content.invoke()
    }
}

@Composable
fun getGradiantBackground(): ShaderBrush {
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(Background, BackgroundVariant),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }
    return largeRadialGradient
}

@Preview
@Composable
fun DefaultScreenPreview() {
    DefaultScreen(Modifier) {
        OnPrimaryText(text = "Test")
    }
}
