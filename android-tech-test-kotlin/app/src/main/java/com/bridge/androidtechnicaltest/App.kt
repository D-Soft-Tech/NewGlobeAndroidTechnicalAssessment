package com.bridge.androidtechnicaltest

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.bridge.androidtechnicaltest.data.worker.factory.PupilSyncWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var pupilsDataSyncWorkerFactory: PupilSyncWorkerFactory

    override fun onCreate() {
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(pupilsDataSyncWorkerFactory).build()
}