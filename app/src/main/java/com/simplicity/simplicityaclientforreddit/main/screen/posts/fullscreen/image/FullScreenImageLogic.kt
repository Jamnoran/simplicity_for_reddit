package com.simplicity.simplicityaclientforreddit.main.screen.posts.fullscreen.image

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetWebUrlDecodedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FullScreenImageLogic : BaseComposeLogic<FullScreenImageInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready(input: FullScreenImageInput) {
        val urlDecoded = GetWebUrlDecodedUseCase(input.inputData).invoke()
        foreground {
            _stateFlow.emit(UiState.Success(Data(urlDecoded)))
        }
    }
}
