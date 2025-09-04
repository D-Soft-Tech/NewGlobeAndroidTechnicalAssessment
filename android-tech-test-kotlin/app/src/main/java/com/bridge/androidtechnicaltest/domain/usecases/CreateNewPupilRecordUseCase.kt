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
class CreateNewPupilRecordUseCase @Inject constructor(
    private val repository: PupilRepository,
    private val dbManager: DbManager
) {
    suspend operator fun invoke(pupilModel: PupilModel): RepositoryResponse<String> {
        return when (val serverResponse = repository.createAPupil(pupilModel)) {
            is RepositoryResponse.Success -> {
                val serverResultWithUpdatedId = serverResponse.data
                RepositoryResponse.Success("Created successfully")
                val affectedRow = dbManager.insertPupils(listOf(serverResultWithUpdatedId))
                if (affectedRow >= 1) RepositoryResponse.Success("Created successfully") else RepositoryResponse.Success(
                    "An unexpected error occurred"
                )
            }

            is RepositoryResponse.Error -> {
                RepositoryResponse.Error(serverResponse.errorMessage)
            }

            is RepositoryResponse.ApiError -> {
                val modifiedModel = pupilModel.copy(
                    modified = true,
                    requiredAction = RequiredModificationAction.SHOULD_BE_CREATED
                )
                val affectedRows = dbManager.insertPupils(listOf(modifiedModel))
                if (affectedRows >= 1) RepositoryResponse.Success("Created successfully") else RepositoryResponse.Success(
                    "An unexpected error occurred"
                )
            }
        }
    }
}