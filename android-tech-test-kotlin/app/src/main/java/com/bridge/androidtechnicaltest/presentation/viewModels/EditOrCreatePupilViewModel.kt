package com.bridge.androidtechnicaltest.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData.getRandomProfileImage
import com.bridge.androidtechnicaltest.di.qualifiers.IoDispatcherScope
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.domain.usecases.CreateNewPupilRecordUseCase
import com.bridge.androidtechnicaltest.domain.usecases.UpdatePupilRecordUseCase
import com.bridge.androidtechnicaltest.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EditOrCreatePupilViewModel @Inject constructor(
    private val updatePupilRecordUseCase: UpdatePupilRecordUseCase,
    private val createNewPupilRecordUseCase: CreateNewPupilRecordUseCase,
    @IoDispatcherScope private val ioDispatcher: CoroutineContext
) : ViewModel() {
    private val _updateRecordFlow: MutableSharedFlow<UiState<String>> = MutableSharedFlow()
    val updateRecordFlow: SharedFlow<UiState<String>> get() = _updateRecordFlow

    private val _createRecordFlow: MutableSharedFlow<UiState<String>> = MutableSharedFlow()
    val createRecordFlow: SharedFlow<UiState<String>> get() = _createRecordFlow

    private val _oldDetails: MutableLiveData<PupilModel> = MutableLiveData()
    private val imageBase64: MutableLiveData<String> = MutableLiveData("")
    val oldDetails: LiveData<PupilModel> get() = _oldDetails
    val pupilId: MutableLiveData<String> = MutableLiveData("")
    val firstName: MutableLiveData<String> = MutableLiveData("")
    val lastName: MutableLiveData<String> = MutableLiveData("")
    val pupilClass: MutableLiveData<String> = MutableLiveData("")
    val guardianPhone: MutableLiveData<String> = MutableLiveData("")
    val age: MutableLiveData<String> = MutableLiveData("")
    val country: MutableLiveData<String> = MutableLiveData("")
    val address: MutableLiveData<String> = MutableLiveData("")

    val allFieldsField: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        val update = {
            value = listOf(
                pupilId.value,
                firstName.value,
                lastName.value,
                pupilClass.value,
                guardianPhone.value,
                age.value,
                country.value,
                address.value
            ).all { !it.isNullOrBlank() }
        }
        addSource(pupilId) { update() }
        addSource(firstName) { update() }
        addSource(lastName) { update() }
        addSource(pupilClass) { update() }
        addSource(guardianPhone) { update() }
        addSource(age) { update() }
        addSource(country) { update() }
        addSource(address) { update() }
    }

    fun setImageBase64String(imageString: String) {
        imageBase64.value = imageString
    }

    fun createOrEditPupil(isCreate: Boolean) {
        var pupilModel = getPupilModel()
        if (imageBase64.value.isNullOrBlank()) {
            if (pupilModel.image.isNotBlank()) {
                pupilModel = pupilModel.copy(image = getRandomProfileImage())
            }
        } else {
            pupilModel = pupilModel.copy(image = imageBase64.value!!)
        }
        if (isCreate) {
            createPupil(pupilModel)
        } else {
            updatePupilRecord(pupilModel)
        }
    }

    private fun createPupil(pupilModel: PupilModel) {
        viewModelScope.launch(ioDispatcher) {
            _createRecordFlow.emit(UiState.Loading)
            val result = createNewPupilRecordUseCase.invoke(pupilModel)
            if (result is RepositoryResponse.Success) {
                _createRecordFlow.emit(
                    UiState.Success(result.data)
                )
            } else {
                _createRecordFlow.emit(
                    UiState.Error((result as RepositoryResponse.Error).errorMessage)
                )
            }
        }
    }

    private fun updatePupilRecord(pupilModel: PupilModel) {
        viewModelScope.launch(ioDispatcher) {
            _updateRecordFlow.emit(UiState.Loading)
            val result = updatePupilRecordUseCase.invoke(pupilModel)
            if (result is RepositoryResponse.Success) {
                _updateRecordFlow.emit(
                    UiState.Success(result.data)
                )
            } else {
                _updateRecordFlow.emit(
                    UiState.Error((result as RepositoryResponse.Error).errorMessage)
                )
            }
        }
    }

    fun setPupilData(pupilModel: PupilModel) {
        _oldDetails.value = pupilModel
        pupilId.value = pupilModel.pupilId.toString()
        firstName.value = pupilModel.name.split(" ").first()
        lastName.value = pupilModel.name.split(" ").last()
        pupilClass.value = pupilModel.pupilClass
        guardianPhone.value = pupilModel.guardianPhoneNumber
        age.value = pupilModel.age.toString()
        country.value = pupilModel.country
        address.value = pupilModel.address
    }

    fun hasPupilRecordsBeenUpdated(): Boolean {
        val updatedPupilModel = getPupilModel()
        return updatedPupilModel === _oldDetails.value
    }

    private fun getPupilModel(): PupilModel {
        val updatedModel = (_oldDetails.value ?: SampleData.getEmptyPupilModel()).copy(
            pupilId = pupilId.value.toString().toInt(),
            name = firstName.value.toString() + " " + lastName.value.toString(),
            pupilClass = pupilClass.value.toString(),
            guardianPhoneNumber = guardianPhone.value.toString(),
            country = country.value.toString(),
            address = address.value.toString()
        )
        return updatedModel.let {
            age.value?.let { it1 ->
                if (it1.isNotBlank()) {
                    it.copy(age = it1.toInt())
                } else it
            } ?: it
        }
    }
}