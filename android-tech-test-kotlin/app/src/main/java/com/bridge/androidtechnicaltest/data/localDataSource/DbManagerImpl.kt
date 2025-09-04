package com.bridge.androidtechnicaltest.data.localDataSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.bridge.androidtechnicaltest.data.localDataSource.db.AppDatabase
import com.bridge.androidtechnicaltest.data.localDataSource.db.PupilsRemoteMediator
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilDao
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilRemoteKeysDao
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilEntity
import com.bridge.androidtechnicaltest.domain.DbManager
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.utils.EntityMapper.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbManagerImpl @Inject constructor(
    private val pupilsRemoteMediator: PupilsRemoteMediator,
    private val pupilDao: PupilDao,
    private val remoteKeysDao: PupilRemoteKeysDao,
    private val appDatabase: AppDatabase
) : DbManager {
    override suspend fun insertPupils(pupils: List<PupilModel>): Int =
        pupilDao.insertPupils(pupils.map { it.toEntity() }).size

    @OptIn(ExperimentalPagingApi::class)
    override fun getPupils(): Pager<Int, PupilModel> =
        Pager(config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            maxSize = 100,
            prefetchDistance = 10,
            initialLoadSize = 15
        ),
            remoteMediator = pupilsRemoteMediator,
            pagingSourceFactory = {
                pupilDao.fetchPupils()
            }
        )

    @OptIn(ExperimentalPagingApi::class)
    override fun getPupilsByName(name: String): Pager<Int, PupilModel> =
        Pager(config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            maxSize = 100,
            prefetchDistance = 10,
            initialLoadSize = 15
        ),
            remoteMediator = pupilsRemoteMediator,
            pagingSourceFactory = {
                pupilDao.fetchPupilsByName("%${name.trim()}%")
            }
        )

    override suspend fun fetchAllPendingUpdates(): List<PupilModel> = pupilDao.fetchAllPendingUpdates()

    override suspend fun fetchPupilById(pupilId: Int): PupilEntity =
        pupilDao.fetchPupilById(pupilId)

    override suspend fun deletePupilRecord(pupilId: Int): Int =
        appDatabase.withTransaction {
            pupilDao.deletePupilRecord(pupilId)
            remoteKeysDao.deleteKeysByPupilId(pupilId)
        }

    override suspend fun deleteAllPupilRecords(): Int =
        appDatabase.withTransaction {
            pupilDao.deleteAllPupilRecords()
            remoteKeysDao.deleteAll()
        }
}