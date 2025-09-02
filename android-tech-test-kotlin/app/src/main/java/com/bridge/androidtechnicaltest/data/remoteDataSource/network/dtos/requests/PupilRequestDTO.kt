package com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.requests

data class PupilRequestDTO(
    val name: String,
    val pupilId: Int,
    val image: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
)