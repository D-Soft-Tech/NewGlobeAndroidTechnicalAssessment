package com.bridge.androidtechnicaltest.presentation.ui.fragments

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentEditPupilDetailsBinding
import com.bridge.androidtechnicaltest.presentation.ui.adapters.loadImageUri
import com.bridge.androidtechnicaltest.presentation.viewModels.EditOrCreatePupilViewModel
import com.bridge.androidtechnicaltest.utils.AppUtils.createAlertDialog
import com.bridge.androidtechnicaltest.utils.AppUtils.getLoadingAlertDialog
import com.bridge.androidtechnicaltest.utils.ExtensionFunctions.collectUiSharedFlow
import com.bridge.androidtechnicaltest.utils.ExtensionFunctions.convertToString
import com.bridge.androidtechnicaltest.utils.ExtensionFunctions.showSnackBarMessage
import com.example.softcam.utils.SoftCam
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateOrEditPupilDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEditPupilDetailsBinding
    private val args: CreateOrEditPupilDetailsFragmentArgs by navArgs()
    private val editOrCreatePupilViewModel: EditOrCreatePupilViewModel by viewModels()
    private var loaderAlertDialog: Dialog? = null
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result?.data?.let {
                    val imageUri: Uri? =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) it.getParcelableExtra(
                            SoftCam.SOFT_CAM_RESULT_KEY,
                            Uri::class.java
                        ) else it.getParcelableExtra(SoftCam.SOFT_CAM_RESULT_KEY)
                    imageUri?.let { uri ->
                        if (uri.toString().isNotBlank()) {
                            binding.profilePicture.loadImageUri(uri)
                            editOrCreatePupilViewModel.setImageBase64String(
                                uri.convertToString(
                                    requireContext()
                                )
                            )
                        } else showSnackBarMessage(
                            getString(R.string.image_capture_failed_retry),
                            true
                        )
                    } ?: run {
                        showSnackBarMessage(
                            getString(R.string.image_capture_failed_retry),
                            true
                        )
                    }
                } ?: run {
                    showSnackBarMessage(
                        getString(R.string.image_capture_failed_retry),
                        true
                    )
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_pupil_details,
            container,
            false
        )
        val fragmentPageTitle = args.pupilModel?.let { getString(R.string.edit_pupil_details) }
            ?: getString(R.string.create_new_pupil)
        binding.apply {
            pageTitle = fragmentPageTitle
            editViewModel = editOrCreatePupilViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loaderAlertDialog = getLoadingAlertDialog(requireContext())
        args.pupilModel?.let {
            editOrCreatePupilViewModel.setPupilData(it)
        }
        binding.captureProfilePictureIcon.setOnClickListener {
            SoftCam.launchSoftCam(cameraLauncher, requireContext())
        }
        binding.imageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.button.setOnClickListener {
            // If no argument was passed to the navArgs, then it is create otherwise it is update
            args.pupilModel?.let {
                editOrCreatePupilViewModel.createOrEditPupil(false)
            } ?: run {
                editOrCreatePupilViewModel.createOrEditPupil(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Collect CreatePupilSharedFlow
        collectUiSharedFlow(
            editOrCreatePupilViewModel.createRecordFlow,
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
                val action =
                    CreateOrEditPupilDetailsFragmentDirections.actionCreateOrEditPupilDetailsFragmentToPupilListFragment()
                findNavController().navigate(action)
            }.show()
        }

        // Collect CreatePupilSharedFlow
        collectUiSharedFlow(
            editOrCreatePupilViewModel.updateRecordFlow,
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
                val action =
                    CreateOrEditPupilDetailsFragmentDirections.actionCreateOrEditPupilDetailsFragmentToPupilListFragment()
                findNavController().navigate(action)
            }.show()
        }
    }
}