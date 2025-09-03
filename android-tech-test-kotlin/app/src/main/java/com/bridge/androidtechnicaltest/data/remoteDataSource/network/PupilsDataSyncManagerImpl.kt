package com.bridge.androidtechnicaltest.data.remoteDataSource.network

import com.bridge.androidtechnicaltest.data.PupilsDataSyncManager
import com.bridge.androidtechnicaltest.domain.DbManager
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction
import com.bridge.androidtechnicaltest.domain.usecases.CreateNewPupilRecordUseCase
import com.bridge.androidtechnicaltest.domain.usecases.DeletePupilRecordUseCase
import com.bridge.androidtechnicaltest.domain.usecases.UpdatePupilRecordUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilsDataSyncManagerImpl @Inject constructor(
    private val dbManager: DbManager,
    private val createPupilUseCase: CreateNewPupilRecordUseCase,
    private val updatePupilUseCase: UpdatePupilRecordUseCase,
    private val deletePupilRecordUseCase: DeletePupilRecordUseCase
) : PupilsDataSyncManager {
    override suspend fun syncData(): Boolean =
        dbManager.fetchAllPendingUpdates().map {
            when (it.requiredAction) {
                RequiredModificationAction.SHOULD_BE_CREATED -> {
                    createPupilUseCase.invoke(it)
                }

                RequiredModificationAction.SHOULD_BE_UPDATED -> {
                    updatePupilUseCase.invoke(it)
                }

                RequiredModificationAction.SHOULD_BE_DELETED -> {
                    deletePupilRecordUseCase.invoke(it)
                }

                RequiredModificationAction.NONE_REQUIRED -> {
                    RepositoryResponse.Success("Successful")
                }
            }
        }.any { it is RepositoryResponse.Error }
}