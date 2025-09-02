package com.bridge.androidtechnicaltest.domain.usecases

import androidx.paging.Pager
import com.bridge.androidtechnicaltest.domain.DbManager
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilsListUseCase @Inject constructor(
    private val dbManager: DbManager
) {
    fun invoke(): Pager<Int, PupilModel> = dbManager.getPupils()
}