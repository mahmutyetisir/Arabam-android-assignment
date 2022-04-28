package arabam.android.assigment.car.list

import android.app.AlertDialog
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import arabam.android.assigment.R
import arabam.android.assigment.car.list.adapter.CarAdapter
import arabam.android.assigment.car.list.adapter.CarLoadStateAdapter
import arabam.android.assigment.car.list.bottomsheet.FilterBottomSheet
import arabam.android.assigment.car.list.bottomsheet.SortDirectionBottomSheet
import arabam.android.assigment.car.list.bottomsheet.SortTypeBottomSheet
import arabam.android.assigment.car.list.event.ListFragmentAction
import arabam.android.assigment.common.addRepeatingJob
import arabam.android.assigment.databinding.FragmentListBinding
import com.myetisir.core.common.viewBinding
import com.myetisir.core.ui.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class ListFragment : BaseFragment<ListViewModel>(R.layout.fragment_list) {
    override val viewModel: ListViewModel by viewModels()
    override val binding by viewBinding(FragmentListBinding::bind)

    private val adapter = CarAdapter(lifecycleScope)

    private var bottomSheet: FilterBottomSheet? = null
    private var sortTypeBottomSheet: SortTypeBottomSheet? = null
    private var sortDirectionBottomSheet: SortDirectionBottomSheet? = null

    private val filterListener = object : (FilterBottomSheet.FilterData) -> Unit {
        override fun invoke(filters: FilterBottomSheet.FilterData) {
            bottomSheet?.dismiss()
            viewModel.filters = filters
            viewModel.fetchCarList()
        }
    }

    private val sortTypeListener = object : (SortTypeBottomSheet.SortTypeData) -> Unit {
        override fun invoke(types: SortTypeBottomSheet.SortTypeData) {
            sortTypeBottomSheet?.dismiss()
            viewModel.sortTypes = types
            viewModel.fetchCarList()
        }
    }

    private val sortDirectionListener = object : (SortDirectionBottomSheet.SortDirectionData) -> Unit {
        override fun invoke(directions: SortDirectionBottomSheet.SortDirectionData) {
            sortDirectionBottomSheet?.dismiss()
            viewModel.sortDirections = directions
            viewModel.fetchCarList()
        }
    }

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = CarLoadStateAdapter(lifecycleScope, adapter::retry)
            )
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            swipeRefresh.setOnRefreshListener {
                viewModel.retry()
            }
            btnFilter.clicks()
                .onEach {
                    viewModel.sendAction(ListFragmentAction.Filter)
                }
                .debounce(CarLoadStateAdapter.LoadStateViewHolder.CLICK_TIMEOUT)
                .launchIn(lifecycleScope)

            btnSortType.clicks()
                .onEach {
                    viewModel.sendAction(ListFragmentAction.SortTypes)
                }
                .debounce(CarLoadStateAdapter.LoadStateViewHolder.CLICK_TIMEOUT)
                .launchIn(lifecycleScope)

            btnOrder.clicks()
                .onEach {
                    viewModel.sendAction(ListFragmentAction.SortDirection)
                }
                .debounce(CarLoadStateAdapter.LoadStateViewHolder.CLICK_TIMEOUT)
                .launchIn(lifecycleScope)
        }
        if (viewModel.needInitRequestSent) {
            viewModel.fetchCarList()
        }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.carsFlow.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }


        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.isRefresh.collect { isRefresh ->
                binding.progressBar.isVisible = isRefresh && adapter.itemCount <= 0
                if (isRefresh.not()) {
                    binding.swipeRefresh.isRefreshing = isRefresh
                }
            }
        }

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            adapter.loadStateFlow.collectLatest {
                it.decideOnState(showLoading = { isLoading ->
                    viewModel.updateRefreshState(isLoading)
                }, showError = { error ->
                    showDialog(error.message)
                })
            }
        }

        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.listFragmentActions.collectLatest {
                when (it) {
                    is ListFragmentAction.CarItemAction -> {
                        val data = Bundle().apply {
                            putString("car_id", it.car?.id.toString())
                        }
                        findNavController().navigate(R.id.detailFragment, data)
                    }
                    ListFragmentAction.Retry -> {
                        viewModel.fetchCarList()
                    }
                    ListFragmentAction.Filter -> {
                        bottomSheet = FilterBottomSheet(viewModel.filters)
                        bottomSheet?.show(
                            childFragmentManager,
                            FilterBottomSheet::class.java.name
                        )
                        bottomSheet?.setOnSubmitListener(
                            this@ListFragment.filterListener
                        )
                    }
                    ListFragmentAction.SortDirection -> {
                        sortDirectionBottomSheet = SortDirectionBottomSheet(viewModel.sortDirections)
                        sortDirectionBottomSheet?.show(
                            childFragmentManager,
                            SortDirectionBottomSheet::class.java.name
                        )
                        sortDirectionBottomSheet?.setOnSubmitListener(sortDirectionListener)
                    }
                    ListFragmentAction.SortTypes -> {
                        sortTypeBottomSheet = SortTypeBottomSheet(viewModel.sortTypes)
                        sortTypeBottomSheet?.show(
                            childFragmentManager,
                            SortTypeBottomSheet::class.java.name
                        )
                        sortTypeBottomSheet?.setOnSubmitListener(sortTypeListener)
                    }
                }
            }
        }
    }

    private fun showDialog(errorMessage: String?) {
        AlertDialog.Builder(requireActivity()).setTitle(getString(R.string.alert_dialog_title))
            .setMessage(getString(R.string.alert_dialog_message, errorMessage))
            .setPositiveButton(getString(R.string.alert_dialog_retry_button)) { v, d ->
                viewModel.retry()
            }.show()
    }

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showError: (Throwable) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)

        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

        errorState?.let { showError(it.error) }
    }

    override fun onBackPressed() {
        findNavController().navigateUp()
    }
}