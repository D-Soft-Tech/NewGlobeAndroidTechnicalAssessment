package com.bridge.androidtechnicaltest.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.api.PupilApi
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.usecases.PupilsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PupilListFragmentViewModel @Inject constructor(
    private val pupilsListUseCase: PupilsListUseCase,
    private val pupilApi: PupilApi,
) : ViewModel() {
    val pupilsList: Flow<PagingData<PupilModel>> = pupilsListUseCase.invoke().flow.cachedIn(viewModelScope)

    fun fetch() {
        viewModelScope.launch {
            pupilApi.getPupils(1)
        }
    }
}