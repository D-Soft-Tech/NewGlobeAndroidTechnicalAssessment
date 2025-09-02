package com.bridge.androidtechnicaltest.utils

import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilEntity
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilRemoteKey
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData.getRandomGuardianAddress
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData.getRandomGuardianPhoneNumber
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData.getRandomPupilAge
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.SampleData.getRandomPupilClass
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.requests.PupilRequestDTO
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses.PupilItemResponseDTO
import com.bridge.androidtechnicaltest.data.remoteDataSource.network.dtos.responses.PupilListResponseDTO
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction

object EntityMapper {
    fun PupilListResponseDTO.toPupilEntity(): List<PupilEntity> =
        items.map {
            PupilEntity(
                it.country,
                it.image,
                it.latitude,
                it.longitude,
                it.name,
                it.pupilId,
                getRandomPupilAge(),
                getRandomPupilClass(),
                getRandomGuardianPhoneNumber(),
                getRandomGuardianAddress(),
                modified = false,
                RequiredModificationAction.NONE_REQUIRED
            )
        }

    fun PupilModel.toPupilRequestDTO(): PupilRequestDTO =
        PupilRequestDTO(name, pupilId, image, country, latitude, longitude)

    fun PupilRequestDTO.toPupilModel(oldModel: PupilModel): PupilModel =
        PupilModel(
            country,
            image,
            latitude,
            longitude,
            name,
            pupilId,
            oldModel.age,
            oldModel.pupilClass,
            oldModel.guardianPhoneNumber,
            oldModel.address,
            false,
            RequiredModificationAction.NONE_REQUIRED
        )

    fun PupilEntity.toDomain(): PupilModel =
        PupilModel(
            country,
            image,
            latitude,
            longitude,
            name,
            pupilId,
            age,
            pupilClass,
            guardianPhoneNumber,
            address,
            modified,
            requiredAction
        )

    fun PupilItemResponseDTO.toEntity(oldModel: PupilModel?): PupilEntity =
        oldModel?.let {
            PupilEntity(
                country,
                image,
                latitude,
                longitude,
                name,
                pupilId,
                it.age,
                it.pupilClass,
                it.guardianPhoneNumber,
                it.address
            )
        } ?: run {
            PupilEntity(
                country,
                image,
                latitude,
                longitude,
                name,
                pupilId,
                getRandomPupilAge(),
                getRandomPupilClass(),
                getRandomGuardianPhoneNumber(),
                getRandomGuardianAddress()
            )
        }

    fun PupilModel.toEntity(): PupilEntity = PupilEntity(
        country,
        image,
        latitude,
        longitude,
        name,
        pupilId,
        age,
        pupilClass,
        guardianPhoneNumber,
        address,
        modified,
        requiredAction
    )

    fun String.getRequiredAction(): RequiredModificationAction =
        when (this) {
            RequiredModificationAction.NONE_REQUIRED.actionName -> RequiredModificationAction.NONE_REQUIRED
            RequiredModificationAction.SHOULD_BE_CREATED.actionName -> RequiredModificationAction.SHOULD_BE_CREATED
            RequiredModificationAction.SHOULD_BE_UPDATED.actionName -> RequiredModificationAction.SHOULD_BE_UPDATED
            RequiredModificationAction.SHOULD_BE_DELETED.actionName -> RequiredModificationAction.SHOULD_BE_DELETED
            else -> RequiredModificationAction.NONE_REQUIRED
        }

    fun PupilListResponseDTO.mapToRemoteKeys(prevKey: Int?, nextKey: Int?): List<PupilRemoteKey> =
        this.items.map {
            PupilRemoteKey(it.pupilId, this.pageNumber, prevKey, nextKey)
        }
}