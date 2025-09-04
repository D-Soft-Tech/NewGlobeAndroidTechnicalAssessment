package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.domain.DbManager
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeletePupilRecordUseCase @Inject constructor(
    private val pupilRepository: PupilRepository,
    private val dbManager: DbManager
) {
    suspend operator fun invoke(pupilModel: PupilModel): RepositoryResponse<String> {
        return when (val remoteResponse = pupilRepository.deletePupilRecord(pupilModel)) {
            is RepositoryResponse.Success -> {
                val rowsAffected = dbManager.deletePupilRecord(pupilModel.pupilId)
                if (rowsAffected >= 1) RepositoryResponse.Success("Deleted successfully") else RepositoryResponse.Error("An unexpected error occurred")
            }

            is RepositoryResponse.Error -> {
                RepositoryResponse.Error(remoteResponse.errorMessage)
            }

            else -> {
                val modifiedModel = pupilModel.copy(
                    modified = true,
                    requiredAction = RequiredModificationAction.SHOULD_BE_DELETED
                )
                val affectedRows = dbManager.insertPupils(listOf(modifiedModel))
                if (affectedRows >= 1) RepositoryResponse.Success("Deleted successfully") else RepositoryResponse.Error("An unexpected error occurred")
            }
        }
    }
}