package com.bridge.androidtechnicaltest.presentation.ui.adapters

import dagger.assisted.AssistedFactory

@AssistedFactory
interface PupilPagingAdapterFactory {
    fun createPupilPagingAdapter(
        onClickItemListener: PupilClickItemListener
    ): PupilPagingAdapter
}