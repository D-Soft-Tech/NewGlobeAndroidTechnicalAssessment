package com.bridge.androidtechnicaltest.data.remoteDataSource.network

import com.bridge.androidtechnicaltest.data.localDataSource.db.PupilList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PupilApi {
    @GET("pupils")
    fun getPupils(@Query("page") page: Int = 1): Response<PupilList>
}