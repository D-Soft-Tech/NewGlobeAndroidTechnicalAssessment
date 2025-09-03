package com.bridge.androidtechnicaltest.presentation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.databinding.FragmentPupillistBinding
import com.bridge.androidtechnicaltest.presentation.ui.adapters.PupilPagingAdapter
import com.bridge.androidtechnicaltest.presentation.ui.adapters.PupilPagingAdapterFactory
import com.bridge.androidtechnicaltest.presentation.viewModels.PupilListFragmentViewModel
import com.bridge.androidtechnicaltest.presentation.viewModels.SyncPupilDataViewModel
import com.bridge.androidtechnicaltest.utils.AppUtils
import com.bridge.androidtechnicaltest.utils.AppUtils.createAlertDialog
import com.bridge.androidtechnicaltest.utils.ExtensionFunctions.collectUiSharedFlow
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PupilListFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentPupillistBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var fab: FloatingActionButton

    @Inject
    lateinit var pupilPagingAdapterFactory: PupilPagingAdapterFactory
    private lateinit var pupilPagingAdapter: PupilPagingAdapter
    private val pupilsListViewModel: PupilListFragmentViewModel by viewModels()
    private val syncDataViewModel: SyncPupilDataViewModel by viewModels()
    private var loaderAlertDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pupillist, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        requireActivity().addMenuProvider(
            this,
            viewLifecycleOwner,
            Lifecycle.State.CREATED
        )
        loaderAlertDialog = AppUtils.getLoadingAlertDialog(requireContext())
        pupilPagingAdapter = pupilPagingAdapterFactory.createPupilPagingAdapter {
            val action =
                PupilListFragmentDirections.actionPupilListFragmentToPupilDetailFragment(it)
            findNavController().navigate(action)
        }
        recyclerView.adapter = pupilPagingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            pupilsListViewModel.pupilsList.collectLatest {
                pupilPagingAdapter.submitData(it)
            }
        }

        fab.setOnClickListener {
            val action =
                PupilListFragmentDirections.actionPupilListFragmentToCreateOrEditPupilDetailsFragment(
                    null
                )
            findNavController().navigate(action)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    pupilsListViewModel.searchPupilByName(it)
                }
                return true
            }
        })
        searchView.setOnCloseListener {
            pupilsListViewModel.searchPupilByName("")
            true
        }
    }

    override fun onResume() {
        super.onResume()
        collectUiSharedFlow(
            syncDataViewModel.syncDataResponse,
            { loaderAlertDialog?.show() },
            {
                loaderAlertDialog?.dismiss()
                requireContext().createAlertDialog(
                    getString(R.string.sync_completed),
                    it,
                    null
                ) {
                    // do nothing
                }.show()
            }
        ) {
            val message =
                if (it.first == it.second) getString(R.string.all_data_successfully_backed_up) else getString(
                    R.string._of_completed,
                    it.first.toString(),
                    it.second.toString()
                )
            loaderAlertDialog?.dismiss()
            requireContext().createAlertDialog(
                getString(R.string.sync_completed),
                message,
                null
            ) {
                // Do nothing
            }.show()
        }
    }

    private fun initViews() {
        with(binding) {
            recyclerView = pupilList
            this@PupilListFragment.searchView = this.searchView
            fab = floatingActionButton
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_reset) {
            syncDataViewModel.synchronizeData()
        }
        return true
    }
}