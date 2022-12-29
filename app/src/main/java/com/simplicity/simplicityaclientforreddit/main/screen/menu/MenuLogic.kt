package com.simplicity.simplicityaclientforreddit.main.screen.menu

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MenuLogic : BaseComposeLogic<MenuInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Success(Data("")))
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready(input: MenuInput) {
        // Do something in the background
    }
}
