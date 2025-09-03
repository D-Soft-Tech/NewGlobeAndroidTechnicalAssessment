package com.bridge.androidtechnicaltest.domain.usecases

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
    suspend operator fun invoke(pupilModel: PupilModel): RepositoryResponse<String> {
        val remoteResponse = repository.updatePupilRecord(pupilModel)
        val affectedRowsInDb = when (remoteResponse) {
            is RepositoryResponse.Success -> {
                val modifiedData = pupilModel.copy(
                    modified = false,
                    requiredAction = RequiredModificationAction.NONE_REQUIRED
                )
                dbManager.insertPupils(listOf(modifiedData))
            }

            else -> {
                val modifiedData = pupilModel.copy(
                    modified = true,
                    requiredAction = RequiredModificationAction.SHOULD_BE_UPDATED
                )
                dbManager.insertPupils(listOf(modifiedData))
            }
        }
        return if (affectedRowsInDb >= 1) RepositoryResponse.Success("Updated successfully") else RepositoryResponse.Error(
            "An unexpected error occurred"
        )
    }
}