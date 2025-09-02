package com.bridge.androidtechnicaltest.data.localDataSource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bridge.androidtechnicaltest.utils.AppConstants.PUPIL_REMOTE_KEYS_TABLE_NAME

@Entity(tableName = PUPIL_REMOTE_KEYS_TABLE_NAME)
data class PupilRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val pupilId: Int,
    val currentPage: Int,
    val previousPage: Int?,
    val nextPage: Int?
)