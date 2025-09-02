package com.bridge.androidtechnicaltest.data.remoteDataSource.network.api

import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.requests.PupilRequestDTO
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses.PupilItemResponseDTO
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses.PupilListResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PupilApi {
    @GET("pupils")
    suspend fun getPupils(@Query("page") page: Int): Response<PupilListResponseDTO>

    @GET("pupils/{pupilId}")
    suspend fun getPupilByPupilId(
        @Path("pupilId") pupilId: Int
    ): Response<PupilItemResponseDTO>

    @POST("pupils")
    suspend fun createAPupil(
        @Body pupil: PupilRequestDTO
    ): Response<PupilItemResponseDTO>

    @PUT("pupils/{pupilId}")
    suspend fun updatePupilRecord(
        @Path("pupilId") pupilId: Int,
        @Body pupil: PupilRequestDTO
    ): Response<PupilItemResponseDTO>

    @DELETE("pupils/{pupilId}")
    suspend fun deletePupilRecord(
        @Path("pupilId") pupilId: Int
    ): Response<Void> // returns a 204 No Content Response
}