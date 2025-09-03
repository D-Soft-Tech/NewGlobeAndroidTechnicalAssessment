package com.bridge.androidtechnicaltest.domain.usecases

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
        val affectedRowsInDb = when (val serverResponse = repository.createAPupil(pupilModel)) {
            is RepositoryResponse.Success -> {
                val serverResultWithUpdatedId = serverResponse.data
                dbManager.insertPupils(listOf(serverResultWithUpdatedId))
            }

            else -> {
                val modifiedModel = pupilModel.copy(
                    modified = true,
                    requiredAction = RequiredModificationAction.SHOULD_BE_CREATED
                )
                dbManager.insertPupils(listOf(modifiedModel))
            }
        }
        return if (affectedRowsInDb >= 1) RepositoryResponse.Success("Deleted successfully") else RepositoryResponse.Error(
            "An unexpected error occurred"
        )
    }
}