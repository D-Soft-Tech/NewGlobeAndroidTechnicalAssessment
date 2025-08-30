package com.bridge.androidtechnicaltest.data.localDataSource.db

import com.bridge.androidtechnicaltest.data.remoteDataSource.network.PupilApi
import retrofit2.Response

interface IPupilRepository {
    fun getOrFetchPupils(): Response<PupilList>
}

class PupilRepository(val database: AppDatabase, val pupilApi: PupilApi) /*IPupilRepository */ {

    fun getOrFetchPupils() /* Response<PupilList> */ {
        // TODO("Continue with the implementation here")
        // return Single.just(PupilList(mutableListOf()))
    }
}