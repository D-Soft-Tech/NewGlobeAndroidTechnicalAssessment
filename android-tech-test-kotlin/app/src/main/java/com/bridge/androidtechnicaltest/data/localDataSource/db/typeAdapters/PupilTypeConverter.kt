package com.bridge.androidtechnicaltest.data.localDataSource.db.typeAdapters

import androidx.room.TypeConverter
import com.bridge.androidtechnicaltest.data.localDataSource.entities.PupilEntity
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import com.bridge.androidtechnicaltest.domain.models.enums.RequiredModificationAction
import com.bridge.androidtechnicaltest.utils.EntityMapper.getRequiredAction
import com.bridge.androidtechnicaltest.utils.EntityMapper.toDomain
import com.bridge.androidtechnicaltest.utils.EntityMapper.toEntity

class PupilTypeConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromModificationActionRequired(modificationAction: RequiredModificationAction): String =
            modificationAction.actionName

        @TypeConverter
        @JvmStatic
        fun toModificationActionRequired(modificationActionAsString: String): RequiredModificationAction =
            modificationActionAsString.getRequiredAction()

        @TypeConverter
        @JvmStatic
        fun fromPupilModel(pupilModel: PupilModel): PupilEntity = pupilModel.toEntity()

        @TypeConverter
        @JvmStatic
        fun toPupilModel(pupilEntity: PupilEntity): PupilModel = pupilEntity.toDomain()
    }
}