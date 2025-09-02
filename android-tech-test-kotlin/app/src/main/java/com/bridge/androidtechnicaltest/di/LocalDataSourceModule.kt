package com.bridge.androidtechnicaltest.di

import android.content.Context
import com.bridge.androidtechnicaltest.data.localDataSource.DbManagerImpl
import com.bridge.androidtechnicaltest.data.localDataSource.db.AppDatabase
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilDao
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilRemoteKeysDao
import com.bridge.androidtechnicaltest.domain.DbManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    @Singleton
    fun providesAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.createAppDataBase(context)

    @Provides
    @Singleton
    fun providesPupilDao(
        appDatabase: AppDatabase
    ): PupilDao = appDatabase.createPupilDao()

    @Provides
    @Singleton
    fun providesPupilRemoteKeysDao(
        appDatabase: AppDatabase
    ): PupilRemoteKeysDao = appDatabase.createPupilRemoteKeysDao()

    @Provides
    @Singleton
    fun providesDbManager(dbManagerImpl: DbManagerImpl): DbManager = dbManagerImpl
}