package com.bridge.androidtechnicaltest.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.data.worker.PupilSyncWorker
import com.bridge.androidtechnicaltest.databinding.ActivityMainBinding
import com.bridge.androidtechnicaltest.utils.AppConstants.WORKER_TAG
import com.bridge.androidtechnicaltest.utils.AppConstants.WORK_TIME_INTERVAL
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pupilsDataSyncWorker: WorkManager? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pupilsDataSyncWorker = WorkManager.getInstance(this)
        pupilsDataSyncWorker?.let {
            scheduleWorkToSynchronizeDataWithBackEnd(it)
        }
    }

    private fun scheduleWorkToSynchronizeDataWithBackEnd(workManager: WorkManager) {
        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val workRequest = PeriodicWorkRequestBuilder<PupilSyncWorker>(
            WORK_TIME_INTERVAL,
            TimeUnit.MINUTES
        ).setConstraints(networkConstraints).build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}