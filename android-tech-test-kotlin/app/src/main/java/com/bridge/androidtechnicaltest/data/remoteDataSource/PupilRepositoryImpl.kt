package com.bridge.androidtechnicaltest.data.remoteDataSource

import com.bridge.androidtechnicaltest.data.remoteDataSource.network.api.PupilApi
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.domain.repository.PupilRepository
import com.bridge.androidtechnicaltest.utils.EntityMapper.toDomain
import com.bridge.androidtechnicaltest.utils.EntityMapper.toEntity
import com.bridge.androidtechnicaltest.utils.EntityMapper.toPupilRequestDTO
import com.bridge.androidtechnicaltest.utils.getServerResponse
import com.bridge.androidtechnicaltest.utils.handleApiHttpException
import com.bridge.androidtechnicaltest.utils.handleException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilRepositoryImpl @Inject constructor(
    private val pupilApi: PupilApi
) : PupilRepository {
    override suspend fun getPupilByPupilId(pupil: PupilModel): RepositoryResponse<PupilModel> =
        try {
            val result = pupilApi.getPupilByPupilId(pupil.pupilId)
            when (val serverResponse = getServerResponse(result)) {
                is RepositoryResponse.Success -> {
                    RepositoryResponse.Success(serverResponse.data.toEntity(pupil).toDomain())
                }

                is RepositoryResponse.ApiError -> RepositoryResponse.ApiError(serverResponse.apiErrorMessage)
                is RepositoryResponse.Error -> RepositoryResponse.Error(serverResponse.errorMessage)
            }
        } catch (e: Exception) {
            RepositoryResponse.Error((handleException<PupilModel>(e) as RepositoryResponse.Error).errorMessage)
        }

    override suspend fun createAPupil(pupil: PupilModel): RepositoryResponse<PupilModel> =
        try {
            val result = pupilApi.createAPupil(pupil.toPupilRequestDTO())
            when (val serverResponse = getServerResponse(result)) {
                is RepositoryResponse.Success -> {
                    RepositoryResponse.Success(serverResponse.data.toEntity(pupil).toDomain())
                }

                is RepositoryResponse.ApiError -> RepositoryResponse.ApiError(serverResponse.apiErrorMessage)
                is RepositoryResponse.Error -> RepositoryResponse.Error(serverResponse.errorMessage)
            }
        } catch (e: Exception) {
            RepositoryResponse.Error((handleException<PupilModel>(e) as RepositoryResponse.Error).errorMessage)
        }

    override suspend fun updatePupilRecord(
        pupil: PupilModel
    ): RepositoryResponse<PupilModel> =
        try {
            val result = pupilApi.updatePupilRecord(pupil.pupilId, pupil.toPupilRequestDTO())
            when (val serverResponse = getServerResponse(result)) {
                is RepositoryResponse.Success -> {
                    RepositoryResponse.Success(serverResponse.data.toEntity(pupil).toDomain())
                }

                is RepositoryResponse.ApiError -> RepositoryResponse.ApiError(serverResponse.apiErrorMessage)
                is RepositoryResponse.Error -> RepositoryResponse.Error(serverResponse.errorMessage)
            }
        } catch (e: Exception) {
            RepositoryResponse.Error((handleException<PupilModel>(e) as RepositoryResponse.Error).errorMessage)
        }

    override suspend fun deletePupilRecord(pupil: PupilModel): RepositoryResponse<String> = try {
        val result = pupilApi.deletePupilRecord(pupil.pupilId)
        if (result.isSuccessful) {
            RepositoryResponse.Success("Record deleted successfully")
        } else {
            when {
                (result.code() in 400..499) -> RepositoryResponse.Error(result.message()) // Catches Client Errors
                result.code() >= 500 -> RepositoryResponse.ApiError(result.message()) // Handles Server Errors
                else -> {
                    val apiError = result.errorBody()?.let { handleApiHttpException(it) }
                    RepositoryResponse.Error(apiError?.message.toString())
                }
            }
        }
    } catch (e: Exception) {
        RepositoryResponse.Error((handleException<PupilModel>(e) as RepositoryResponse.Error).errorMessage)
    }
}