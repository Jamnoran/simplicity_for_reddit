package com.simplicity.simplicityaclientforreddit.main.base.compose

sealed class UiState<out T : Any> {
    data class Loading(val loadingMessage: String? = null) : UiState<Nothing>()
    data class Error(val errorMessage: String) : UiState<Nothing>()
    data class Success<T : Any>(val data: T) : UiState<T>()
    data class Empty(val emptyMessage: String? = null) : UiState<Nothing>()
}
