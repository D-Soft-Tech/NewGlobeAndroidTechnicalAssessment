package com.bridge.androidtechnicaltest.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.di.qualifiers.IoDispatcherScope
import com.bridge.androidtechnicaltest.domain.usecases.SyncPupilDataUseCase
import com.bridge.androidtechnicaltest.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SyncPupilDataViewModel @Inject constructor(
    private val dataSynUseCase: SyncPupilDataUseCase,
    @IoDispatcherScope private val ioDispatcher: CoroutineContext
) : ViewModel() {
    private val _syncDataResponse: MutableSharedFlow<UiState<Boolean>> = MutableSharedFlow()
    val syncDataResponse: SharedFlow<UiState<Boolean>> get() = _syncDataResponse

    fun synchronizeData() {
        viewModelScope.launch(ioDispatcher) {
            _syncDataResponse.emit(UiState.Loading)
            _syncDataResponse.emit(
                UiState.Success(dataSynUseCase.invoke())
            )
        }
    }
}