package com.simplicity.simplicityaclientforreddit.main.screen.settings

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsLogic : BaseComposeLogic<SettingsInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready(input: SettingsInput) {
        background {
            // Do something in the background
            foreground {
                loggI("Emitting success to screen")
                _stateFlow.emit(UiState.Success(Data(input.inputData)))
            }
        }
    }
}
