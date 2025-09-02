package com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses

data class PupilItemResponseDTO(
    val country: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val pupilId: Int
)