package com.bridge.androidtechnicaltest.data.localDataSource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilDao
import com.bridge.androidtechnicaltest.data.localDataSource.db.daos.PupilRemoteKeysDao
import com.bridge.androidtechnicaltest.data.localDataSource.db.typeAdapters.PupilTypeConverter
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilEntity
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilRemoteKey
import com.bridge.androidtechnicaltest.utils.AppConstants.APP_DB_NAME
import com.bridge.androidtechnicaltest.utils.AppConstants.APP_DB_VERSION

@Database(entities = [PupilEntity::class, PupilRemoteKey::class], version = APP_DB_VERSION, exportSchema = false)
@TypeConverters(PupilTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun createPupilDao(): PupilDao
    abstract fun createPupilRemoteKeysDao(): PupilRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun createAppDataBase(context: Context): AppDatabase = synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, APP_DB_NAME)
                .fallbackToDestructiveMigration().build().also {
                INSTANCE = it
            }
        }
    }
}