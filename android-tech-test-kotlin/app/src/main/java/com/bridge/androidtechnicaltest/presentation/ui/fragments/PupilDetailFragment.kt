package com.bridge.androidtechnicaltest.presentation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentPupildetailBinding
import com.bridge.androidtechnicaltest.presentation.viewModels.PupilDetailsFragmentViewModel
import com.bridge.androidtechnicaltest.utils.AppUtils
import com.bridge.androidtechnicaltest.utils.AppUtils.createAlertDialog
import com.bridge.androidtechnicaltest.utils.AppUtils.getLoadingAlertDialog
import com.bridge.androidtechnicaltest.utils.ExtensionFunctions.collectUiSharedFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PupilDetailFragment : Fragment() {
    private lateinit var binding: FragmentPupildetailBinding
    private val args: PupilDetailFragmentArgs by navArgs()
    private val viewModel: PupilDetailsFragmentViewModel by viewModels()
    private var loaderAlertDialog: Dialog? = null
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
        loaderAlertDialog = getLoadingAlertDialog(requireContext())
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
                viewModel.deletePupilRecord(args.pupilModel)
            }.show()
        }
    }

    override fun onResume() {
        super.onResume()
        collectUiSharedFlow(
            viewModel.deleteRecordFlow,
            { loaderAlertDialog?.show() },
            {
                loaderAlertDialog?.dismiss()
                requireContext().createAlertDialog(
                    getString(R.string.failed),
                    it,
                    null
                ) {
                    // do nothing
                }.show()
            }
        ) {
            loaderAlertDialog?.dismiss()
            requireContext().createAlertDialog(
                getString(R.string.successful),
                it,
                null
            ) {
                findNavController().popBackStack()
            }.show()
        }
    }
}