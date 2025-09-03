package com.bridge.androidtechnicaltest.data

interface PupilsDataSyncManager {
    suspend fun syncData(): Boolean
}