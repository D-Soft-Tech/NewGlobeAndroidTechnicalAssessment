package com.bridge.androidtechnicaltest.domain.usecases

import androidx.paging.Pager
import com.bridge.androidtechnicaltest.domain.DbManager
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchPupilByNameUseCase @Inject constructor(
    private val dbManager: DbManager
) {
    operator fun invoke(name: String): Pager<Int, PupilModel> = dbManager.getPupilsByName(name)
}