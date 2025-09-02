package com.bridge.androidtechnicaltest.domain.models

import android.os.Parcelable
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction
import kotlinx.parcelize.Parcelize

@Parcelize
data class PupilModel(
    val country: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val pupilId: Int,
    val age: Int,
    val pupilClass: String,
    val guardianPhoneNumber: String,
    val address: String,
    val modified: Boolean = false,
    val requiredAction: RequiredModificationAction = RequiredModificationAction.NONE_REQUIRED,
) : Parcelable
