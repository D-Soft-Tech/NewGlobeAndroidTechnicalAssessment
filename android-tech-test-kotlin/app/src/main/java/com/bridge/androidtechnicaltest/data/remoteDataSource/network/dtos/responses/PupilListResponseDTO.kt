package com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses

data class PupilListResponseDTO(
    val itemCount: Int,
    val items: List<PupilItemResponseDTO>,
    val pageNumber: Int,
    val totalPages: Int
)