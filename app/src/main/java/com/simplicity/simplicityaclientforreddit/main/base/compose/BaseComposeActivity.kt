package com.simplicity.simplicityaclientforreddit.main.base.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

open class BaseComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TAG", "Starting base component activity")
        init()
        setContent {
            SimplicityAClientForRedditTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen()
                }
            }
        }
        savedInstanceState?.let {
            startWithInput(it)
        }
    }

    @Composable
    open fun Screen() {}
    open fun init() { }
    open fun startWithInput(bundle: Bundle) { }
}
