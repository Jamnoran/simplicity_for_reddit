package com.simplicity.simplicityaclientforreddit.main.screen.webview

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetWebUrlDecodedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WebViewLogic : BaseLogic() {
    private val _state = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val state: StateFlow<UiState<Data>> = _state

    fun init(url: String) {
        background {
            val urlDecoded = GetWebUrlDecodedUseCase(url).invoke()
            foreground {
                Log.i("WebViewLogic", "Opening this page: $urlDecoded with the original value of $url")
                _state.emit(UiState.Success(Data(urlDecoded)))
            }
        }
    }
}
