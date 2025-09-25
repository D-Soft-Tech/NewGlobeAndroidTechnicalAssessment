package com.bridge.androidtechnicaltest.utils

import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses.ApiErrorResponse
import com.bridge.androidtechnicaltest.domain.models.RepositoryResponse
import com.bridge.androidtechnicaltest.utils.AppConstants.INTERNET_ERROR
import com.bridge.androidtechnicaltest.utils.AppConstants.SERVER_ERROR
import com.bridge.androidtechnicaltest.utils.AppConstants.UN_EXPECTED_ERROR
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> getServerResponse(result: Response<T>): RepositoryResponse<T> {
    return when {
        result.isSuccessful && result.body() != null -> RepositoryResponse.Success(result.body()!! as T)
        (result.code() in 400..499) -> RepositoryResponse.Error(result.message()) // Catches Client Errors
        result.code() >= 500 -> RepositoryResponse.ApiError(result.message()) // Handles Server Errors
        else -> {
            val apiError = result.errorBody()?.let { handleApiHttpException(it) }
            RepositoryResponse.Error(apiError?.message.toString())
        }
    }
}

fun <T> handleException(e: Exception): RepositoryResponse<T> {
    return when (e) {
        is IOException -> RepositoryResponse.ApiError(INTERNET_ERROR)
        is HttpException -> RepositoryResponse.ApiError(SERVER_ERROR)
        else -> RepositoryResponse.Error(e.message.toString())
    }
}

fun handleApiHttpException(e: ResponseBody): ApiErrorResponse {
    return try {
        e.source().let {
            GsonBuilder().create().fromJson(e.charStream(), ApiErrorResponse::class.java)
        }
    } catch (t: Throwable) {
        ApiErrorResponse(UN_EXPECTED_ERROR)
    }
}