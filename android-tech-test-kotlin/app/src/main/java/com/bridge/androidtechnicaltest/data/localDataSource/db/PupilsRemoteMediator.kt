package com.bridge.androidtechnicaltest.data.localDataSource.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilDao
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilRemoteKeysDao
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilRemoteKey
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.api.PupilApi
import com.bridge.androidtechnicaltest.di.qualifiers.IoDispatcherScope
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.utils.AppConstants.UN_EXPECTED_ERROR
import com.bridge.androidtechnicaltest.utils.EntityMapper.mapToRemoteKeys
import com.bridge.androidtechnicaltest.utils.EntityMapper.toPupilEntity
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalPagingApi::class)
class PupilsRemoteMediator @Inject constructor(
    private val appDatabase: AppDatabase,
    private val pupilApi: PupilApi,
    @IoDispatcherScope private val ioDispatcher: CoroutineContext
) : RemoteMediator<Int, PupilModel>() {
    private val pupilDao: PupilDao = appDatabase.createPupilDao()
    private val remoteKeysDao: PupilRemoteKeysDao = appDatabase.createPupilRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PupilModel>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.previousPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            pupilApi.getPupils(page).body()?.let {
                val endOfPaginationReached = it.items.isEmpty()
                if (loadType == LoadType.REFRESH) {
                    appDatabase.withTransaction {
                        pupilDao.deleteAllPupilRecords()
                        remoteKeysDao.deleteAll()
                    }
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = it.mapToRemoteKeys(prevKey, nextKey)

                appDatabase.withTransaction {
                    remoteKeysDao.insertKey(remoteKeys)
                    pupilDao.insertPupils(it.toPupilEntity())
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } ?: MediatorResult.Error(Throwable(UN_EXPECTED_ERROR))

        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PupilModel>): PupilRemoteKey? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.pupilId?.let { pupilId ->
                remoteKeysDao.getRemoteKeys(pupilId).firstOrNull()
            }
        }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PupilModel>): PupilRemoteKey? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { pupil ->
            remoteKeysDao.getRemoteKeys(pupil.pupilId).firstOrNull()
        }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PupilModel>): PupilRemoteKey? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { pupil ->
            remoteKeysDao.getRemoteKeys(pupil.pupilId).lastOrNull()
        }

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH
}