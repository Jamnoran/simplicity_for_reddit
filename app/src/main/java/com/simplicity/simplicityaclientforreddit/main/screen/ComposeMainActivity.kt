package com.simplicity.simplicityaclientforreddit.main.screen

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeActivity
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class ComposeMainActivity : BaseComposeActivity() {
    private var navController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.simplicity.simplicityaclientforreddit.main.Global.applicationContext = applicationContext
        // Set up coil gif handling
        val imageLoader = ImageLoader.Builder(this)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        com.simplicity.simplicityaclientforreddit.main.Global.imageLoader = imageLoader
    }

    override fun init() {
        Log.i("ComposeMainActivity", "Init")
    }

    @Composable
    override fun Screen() {
        val navigationListener = NavigationListener { intent ->
            startActivity(intent)
        }
        navController = rememberNavController()
        navController?.let { navigator ->
            Navigation(navigationListener = navigationListener, navController = navigator)
        }
    }
}

