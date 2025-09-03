package com.bridge.androidtechnicaltest.domain.usecases

import com.bridge.androidtechnicaltest.data.PupilsDataSyncManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncPupilDataUseCase @Inject constructor(
    private val pupilsDataSyncManager: PupilsDataSyncManager
) {
    suspend operator fun invoke(): Boolean = pupilsDataSyncManager.syncData()
}