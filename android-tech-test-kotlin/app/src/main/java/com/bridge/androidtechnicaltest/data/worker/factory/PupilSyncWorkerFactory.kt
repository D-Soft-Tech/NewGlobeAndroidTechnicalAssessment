package com.bridge.androidtechnicaltest.data.worker.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.bridge.androidtechnicaltest.data.PupilsDataSyncManager
import com.bridge.androidtechnicaltest.data.worker.PupilSyncWorker
import javax.inject.Inject

class PupilSyncWorkerFactory @Inject constructor(
    private val pupilsDataSyncManager: PupilsDataSyncManager
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        when (workerClassName) {
            PupilSyncWorker::class.java.name -> PupilSyncWorker(
                appContext,
                workerParameters,
                pupilsDataSyncManager
            )

            else -> null
        }
}