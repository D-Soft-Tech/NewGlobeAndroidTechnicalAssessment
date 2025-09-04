package com.bridge.androidtechnicaltest.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.api.PupilApi
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.usecases.PupilsListUseCase
import com.bridge.androidtechnicaltest.domain.usecases.SearchPupilByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PupilListFragmentViewModel @Inject constructor(
    private val pupilsListUseCase: PupilsListUseCase,
    private val searchPupilByNameUseCase: SearchPupilByNameUseCase
) : ViewModel() {
    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val pupilsList: Flow<PagingData<PupilModel>> = searchQuery.flatMapLatest { query ->
        query.let {
            if (it.isNotBlank()) searchPupilByNameUseCase.invoke(it) else pupilsListUseCase.invoke()
        }.flow.cachedIn(viewModelScope)
    }
    val pupilsList2: Flow<PagingData<PupilModel>> =
        pupilsListUseCase.invoke().flow.cachedIn(viewModelScope)

    fun searchPupilByName(pupilName: String) {
        searchQuery.value = pupilName
    }
}