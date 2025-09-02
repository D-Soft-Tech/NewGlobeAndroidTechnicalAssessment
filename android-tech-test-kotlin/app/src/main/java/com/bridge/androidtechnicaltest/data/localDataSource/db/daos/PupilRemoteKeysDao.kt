package com.bridge.androidtechnicaltest.data.localDataSource.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilRemoteKey

@Dao
interface PupilRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(pupilRemoteKey: List<PupilRemoteKey>)

    @Query("DELETE FROM PupilRemoteKeyTable WHERE pupilId = :pupilId")
    suspend fun deleteKeysByPupilId(pupilId: Int): Int

    @Query("SELECT * FROM PupilRemoteKeyTable WHERE pupilId = :pupilId")
    suspend fun getRemoteKeys(pupilId: Int): List<PupilRemoteKey>

    @Query("DELETE FROM PupilRemoteKeyTable")
    suspend fun deleteAll(): Int
}