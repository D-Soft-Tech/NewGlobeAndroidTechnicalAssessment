package com.bridge.androidtechnicaltest.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.PupilListItemLayoutBinding
import com.bridge.androidtechnicaltest.domain.PupilsRecyclerViewBindingInterface
import com.bridge.androidtechnicaltest.domain.models.PupilModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.logging.HttpLoggingInterceptor

typealias PupilClickItemListener = (clickedPupil: PupilModel) -> Unit

class PupilPagingAdapter @AssistedInject constructor(
    @Assisted private val onPupilClicked: PupilClickItemListener
) :
    PagingDataAdapter<PupilModel, PupilPagingAdapter.ViewHolder>(COMPARATOR) {
    inner class ViewHolder(val view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(bindingInterface: PupilsRecyclerViewBindingInterface) {
            bindingInterface.bindData(view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = DataBindingUtil.inflate<PupilListItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.pupil_list_item_layout,
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bindingData: PupilsRecyclerViewBindingInterfaceImpl? =
            getItem(position)?.let { PupilsRecyclerViewBindingInterfaceImpl(it) }
        bindingData?.let {
            holder.apply {
                bind(bindingData)
                itemView.findViewById<ImageView>(R.id.view_pupil_details).setOnClickListener {
                    getItem(position)?.let { clickedPupil ->
                        onPupilClicked.invoke(clickedPupil)
                    }
                }
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PupilModel>() {
            override fun areItemsTheSame(oldItem: PupilModel, newItem: PupilModel): Boolean =
                oldItem.pupilId == newItem.pupilId

            override fun areContentsTheSame(oldItem: PupilModel, newItem: PupilModel): Boolean =
                oldItem == newItem
        }
    }
}