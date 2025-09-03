package com.bridge.androidtechnicaltest.presentation.ui

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T, val data2: T? = null) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
}