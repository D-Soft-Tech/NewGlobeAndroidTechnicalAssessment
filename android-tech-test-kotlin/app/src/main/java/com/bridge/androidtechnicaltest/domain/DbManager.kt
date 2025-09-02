package com.bridge.androidtechnicaltest.domain

import androidx.paging.Pager
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilEntity
import com.bridge.androidtechnicaltest.domain.models.PupilModel

interface DbManager {
    suspend fun insertPupils(pupils: List<PupilModel>): Int
    fun getPupils(): Pager<Int, PupilModel>

    suspend fun fetchPupilById(pupilId: Int): PupilEntity

    suspend fun deletePupilRecord(pupilId: Int): Int

    suspend fun deleteAllPupilRecords(): Int
}