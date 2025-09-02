package com.bridge.androidtechnicaltest.domain.models

sealed class RepositoryResponse<out T> {
    class Success<out T>(val data: T): RepositoryResponse<T>()
    class Error(val errorMessage: String): RepositoryResponse<Nothing>()
    class ApiError(val apiErrorMessage: String): RepositoryResponse<Nothing>()
}