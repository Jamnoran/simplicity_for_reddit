package com.simplicity.simplicityaclientforreddit.main.screen.test

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TestLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<String>> = _stateFlow

    fun start() {
        foreground {
            _stateFlow.emit(UiState.Success("Wohoo"))
        }
    }
}
