package com.bridge.androidtechnicaltest.db

import com.bridge.androidtechnicaltest.network.PupilApi
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