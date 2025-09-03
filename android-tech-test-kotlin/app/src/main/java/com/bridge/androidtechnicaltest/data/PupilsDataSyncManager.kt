package com.bridge.androidtechnicaltest.data

interface PupilsDataSyncManager {
    /**
     * Syncs the roomDb data to the backend
     *
     * @return [Pair]<[Int], [Int]> where the first item is the number of successfully backed up data while the last item is the total number of the data.
     * */
    suspend fun syncData(): Pair<Int, Int>
}