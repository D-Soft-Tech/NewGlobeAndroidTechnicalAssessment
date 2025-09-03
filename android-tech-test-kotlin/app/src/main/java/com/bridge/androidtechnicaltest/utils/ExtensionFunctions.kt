package com.bridge.androidtechnicaltest.utils

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bridge.androidtechnicaltest.presentation.ui.UiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStream

object ExtensionFunctions {
    fun Fragment.showSnackBarMessage(message: String, isLong: Boolean = false) {
        requireParentFragment().view?.let {
            Snackbar.make(
                it, message,
                if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    fun Uri.convertToString(context: Context): String {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(this)
            val bytes = inputStream?.readBytes() ?: byteArrayOf()
            inputStream?.close()
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun <T> Fragment.collectUiStateFlow(
        state: StateFlow<UiState<T>>,
        onLoading: (() -> Unit)?,
        onError: ((errorMessage: String) -> Unit)?,
        onSuccess: (result: T) -> Unit
    ) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                state.collectLatest {
                    when (it) {
                        is UiState.Success -> onSuccess.invoke(it.data)
                        is UiState.Error -> onError?.invoke(it.error)
                        is UiState.Loading -> onLoading?.invoke()
                    }
                }
            }
        }
    }

    fun <T> Fragment.collectUiSharedFlow(
        state: SharedFlow<UiState<T>>,
        onLoading: (() -> Unit)?,
        onError: ((errorMessage: String) -> Unit)?,
        onSuccess: (result: T) -> Unit
    ) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                state.collectLatest {
                    when (it) {
                        is UiState.Success -> onSuccess.invoke(it.data)
                        is UiState.Error -> onError?.invoke(it.error)
                        is UiState.Loading -> onLoading?.invoke()
                    }
                }
            }
        }
    }
}