package com.bridge.androidtechnicaltest.data.localDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction
import com.bridge.androidtechnicaltest.utils.AppConstants.PUPIL_ENTITY_TABLE_NAME

@Entity(tableName = PUPIL_ENTITY_TABLE_NAME)
data class PupilEntity(
    val country: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val pupilId: Int,
    val age: Int,
    val pupilClass: String,
    val guardianPhoneNumber: String,
    val address: String,
    val modified: Boolean = false,
    val requiredAction: RequiredModificationAction = RequiredModificationAction.NONE_REQUIRED
)