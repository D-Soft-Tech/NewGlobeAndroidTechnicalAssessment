package com.bridge.androidtechnicaltest.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditOrCreatePupilViewModel @Inject constructor() : ViewModel() {
    private val _oldDetails: MutableLiveData<PupilModel> = MutableLiveData()
    val oldDetails: LiveData<PupilModel> get() = _oldDetails
    val pupilId: MutableLiveData<String> = MutableLiveData("")
    val firstName: MutableLiveData<String> = MutableLiveData("")
    val lastName: MutableLiveData<String> = MutableLiveData("")
    val pupilClass: MutableLiveData<String> = MutableLiveData("")
    val guardianPhone: MutableLiveData<String> = MutableLiveData("")
    val age: MutableLiveData<String> = MutableLiveData("")
    val country: MutableLiveData<String> = MutableLiveData("")
    val address: MutableLiveData<String> = MutableLiveData("")

//    val isUpdated: LiveData<Boolean> get() =

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
        val updatedPupilModel = _oldDetails.value!!.copy(
            name = firstName.value.toString() + " " + lastName.value.toString(),
            pupilClass = pupilClass.value.toString(),
            guardianPhoneNumber = guardianPhone.value.toString(),
            country = country.value.toString(),
            address = address.value.toString()
        ).let {
            age.value?.let {it1 ->
                if (it1.isNotBlank()) {
                    it.copy(age = it1.toInt())
                } else it
            } ?: it
        }
        return updatedPupilModel === _oldDetails.value
    }
}