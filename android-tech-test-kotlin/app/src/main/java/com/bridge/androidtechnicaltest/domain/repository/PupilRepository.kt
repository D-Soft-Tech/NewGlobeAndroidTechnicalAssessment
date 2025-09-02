package com.bridge.androidtechnicaltest.domain.repository

import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse

interface PupilRepository {
    suspend fun getPupilByPupilId(
        pupil: PupilModel
    ): RepositoryResponse<PupilModel>

    suspend fun createAPupil(
        pupil: PupilModel
    ): RepositoryResponse<PupilModel>

    suspend fun updatePupilRecord(
        pupil: PupilModel
    ): RepositoryResponse<PupilModel>

    suspend fun deletePupilRecord(
        pupil: PupilModel
    ): RepositoryResponse<String>
}