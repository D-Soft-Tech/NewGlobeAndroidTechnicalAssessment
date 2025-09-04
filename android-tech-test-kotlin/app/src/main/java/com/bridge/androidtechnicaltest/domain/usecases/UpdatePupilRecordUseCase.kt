package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData.getRandomProfileImage
import com.bridge.androidtechnicaltest.domain.DbManager
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdatePupilRecordUseCase @Inject constructor(
    private val repository: PupilRepository,
    private val dbManager: DbManager
) {
    suspend operator fun invoke(pupilModel: PupilModel): RepositoryResponse<String> =
        when (val remoteResponse = repository.updatePupilRecord(pupilModel)) {
            is RepositoryResponse.Success -> {
                val modifiedData = pupilModel.copy(
                    modified = false,
                    requiredAction = RequiredModificationAction.NONE_REQUIRED
                )
                val rows = dbManager.insertPupils(listOf(modifiedData))
                if (rows >= 1) RepositoryResponse.Success("Created successfully") else RepositoryResponse.Success(
                    "An unexpected error occurred"
                )
            }

            is RepositoryResponse.Error -> {
                RepositoryResponse.Error(remoteResponse.errorMessage)
            }

            else -> {
                val modifiedData = pupilModel.copy(
                    modified = true,
                    requiredAction = RequiredModificationAction.SHOULD_BE_UPDATED
                )
                val rowsAffected = dbManager.insertPupils(listOf(modifiedData))
                if (rowsAffected >= 1) RepositoryResponse.Success("Created successfully") else RepositoryResponse.Success(
                    "An unexpected error occurred"
                )
            }
        }
}