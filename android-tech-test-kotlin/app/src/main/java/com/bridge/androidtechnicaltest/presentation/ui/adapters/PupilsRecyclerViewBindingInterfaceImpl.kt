package com.bridge.androidtechnicaltest.presentation.ui.adapters

import androidx.databinding.ViewDataBinding
import com.bridge.androidtechnicaltest.databinding.PupilListItemLayoutBinding
import com.bridge.androidtechnicaltest.domain.PupilsRecyclerViewBindingInterface
import com.bridge.androidtechnicaltest.domain.models.PupilModel

class PupilsRecyclerViewBindingInterfaceImpl(private val item: PupilModel) :
    PupilsRecyclerViewBindingInterface {
    override fun bindData(view: ViewDataBinding) {
        (view as PupilListItemLayoutBinding).apply {
            pupil = item
        }
    }
}