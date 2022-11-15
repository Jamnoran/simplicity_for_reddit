package com.simplicity.simplicityaclientforreddit.main.screen.settings

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<String>> = _stateFlow

    fun start() {
        foreground {
            _stateFlow.emit(UiState.Success("Init"))
        }
    }

    fun test(s: String) {
        foreground {
            _stateFlow.emit(UiState.Success(s))
        }
    }
}
