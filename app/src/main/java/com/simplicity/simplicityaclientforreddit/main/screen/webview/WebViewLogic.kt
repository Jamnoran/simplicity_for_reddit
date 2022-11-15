package com.simplicity.simplicityaclientforreddit.main.screen.webview

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class WebViewLogic : BaseLogic() {
    private val _state = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val state: StateFlow<UiState<Data>> = _state

    fun init(url: String) {
        background {
            var urlDecoded = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
            if (urlDecoded.startsWith("{")) {
                urlDecoded = urlDecoded.drop(1)
            }
            if (urlDecoded.endsWith("}")) {
                urlDecoded = urlDecoded.dropLast(1)
            }
            foreground {
                Log.i("WebViewLogic", "Opening this page: $urlDecoded with the original value of $url")
                _state.emit(UiState.Success(Data(urlDecoded)))
            }
        }
    }
}
