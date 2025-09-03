package com.bridge.androidtechnicaltest.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.di.qualifiers.IoDispatcherScope
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.domain.usecases.DeletePupilRecordUseCase
import com.bridge.androidtechnicaltest.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class PupilDetailsFragmentViewModel @Inject constructor(
    private val deletePupilRecordUseCase: DeletePupilRecordUseCase,
    @IoDispatcherScope private val ioDispatcher: CoroutineContext
) : ViewModel() {
    private val _deleteRecordFlow: MutableSharedFlow<UiState<String>> = MutableSharedFlow()
    val deleteRecordFlow: SharedFlow<UiState<String>> get() = _deleteRecordFlow

    fun deletePupilRecord(pupilModel: PupilModel) {
        viewModelScope.launch(ioDispatcher) {
            _deleteRecordFlow.emit(UiState.Loading)
            val result = deletePupilRecordUseCase.invoke(pupilModel)
            if (result is RepositoryResponse.Success) {
                _deleteRecordFlow.emit(
                    UiState.Success(result.data)
                )
            } else {
                _deleteRecordFlow.emit(
                    UiState.Error((result as RepositoryResponse.Error).errorMessage)
                )
            }
        }
    }
}