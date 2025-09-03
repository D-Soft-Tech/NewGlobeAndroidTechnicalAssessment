package com.bridge.androidtechnicaltest.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bridge.androidtechnicaltest.data.PupilsDataSyncManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PupilSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workParams: WorkerParameters,
    private val pupilsDataSyncManager: PupilsDataSyncManager
) : CoroutineWorker(context, workParams) {
    override suspend fun doWork(): Result {
        val containsAtLeastOneFailedResponse = pupilsDataSyncManager.syncData().let {
            it.first == it.second
        }
        return if (containsAtLeastOneFailedResponse) Result.success() else Result.retry()
    }
}