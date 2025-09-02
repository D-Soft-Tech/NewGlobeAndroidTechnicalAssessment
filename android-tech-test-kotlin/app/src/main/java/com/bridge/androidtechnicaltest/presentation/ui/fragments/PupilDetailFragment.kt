package com.bridge.androidtechnicaltest.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentPupildetailBinding
import com.bridge.androidtechnicaltest.utils.AppUtils.createAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PupilDetailFragment : Fragment() {
    private lateinit var binding: FragmentPupildetailBinding
    private val args: PupilDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pupildetail, container, false)
        binding.apply {
            pupilDetail = args.pupilModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.editPupilDetailsIcon.setOnClickListener {
            val action =
                PupilDetailFragmentDirections.actionPupilDetailFragmentToCreateOrEditPupilDetailsFragment(
                    args.pupilModel
                )
            findNavController().navigate(action)
        }
        binding.deletePupilRecordIcon.setOnClickListener {
            requireContext().createAlertDialog(
                getString(R.string.are_you_sure),
                getString(R.string.you_are_about_to_delete, args.pupilModel.name),
                null
            ) {
                // Delete operation
            }.show()
        }
    }
}