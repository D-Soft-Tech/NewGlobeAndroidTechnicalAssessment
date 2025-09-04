package com.bridge.androidtechnicaltest.data.localDataSource.db.daos;

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilEntity
import com.bridge.androidtechnicaltest.domain.models.PupilModel

@Dao
public interface PupilDao {
    @Upsert
    suspend fun insertPupils(pupils: List<PupilEntity>): List<Long>

    @Query("SELECT * FROM PupilTable WHERE requiredAction != 'should_be_deleted' ORDER BY pupilId ASC")
    fun fetchPupils(): PagingSource<Int, PupilModel>

    @Query("SELECT * FROM PupilTable WHERE LOWER(name) LIKE LOWER(:pupilName) AND requiredAction != 'should_be_deleted' ORDER BY pupilId ASC")
    fun fetchPupilsByName(pupilName: String): PagingSource<Int, PupilModel>

    @Query("SELECT * FROM PupilTable WHERE modified = 1 ORDER BY pupilId ASC")
    suspend fun fetchAllPendingUpdates(): List<PupilModel>

    @Query("SELECT * FROM PUPILTABLE WHERE pupilId = :pupilId")
    suspend fun fetchPupilById(pupilId: Int): PupilEntity

    @Query("DELETE FROM PupilTable WHERE pupilId = :pupilId")
    suspend fun deletePupilRecord(pupilId: Int): Int

    @Query("DELETE FROM PupilTable")
    suspend fun deleteAllPupilRecords(): Int
}
