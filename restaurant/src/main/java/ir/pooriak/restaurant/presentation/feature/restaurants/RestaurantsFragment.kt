package ir.pooriak.restaurant.presentation.feature.restaurants

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pooriak.core.view.fragment.BaseFragment
import ir.pooriak.restaurant.R
import ir.pooriak.restaurant.databinding.FragmentRestaurantsBinding
import ir.pooriak.restaurant.presentation.feature.restaurants.adapter.RestaurantsAdapter
import ir.pooriak.restaurant.presentation.utils.SortType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by POORIAK on 13,September,2023
 */
class RestaurantsFragment : BaseFragment() {

    private var _mBinding: FragmentRestaurantsBinding? = null
    private val binding get() = _mBinding!!

    private val adapter: RestaurantsAdapter by inject()
    private val viewModel: RestaurantsViewModel by viewModel()
    private val sortList by lazy {
        SortType.filterList().map { getString(it.value) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        viewModel.event(RestaurantsEvent.Restaurants)
        return binding.root
    }

    override fun setupView() {
        toolbarTitle(getString(R.string.restaurants))
        toolbarEndIcon(R.drawable.ic_baseline_filter_alt_24) {
            showFilterPopup(it, sortList)
        }
        visibilityToolbarAction(false)
        setupRecyclerView()
    }

    private fun showFilterPopup(view: View, sortList: List<String>) {
        val popup = PopupMenu(requireContext(), view)
        sortList.forEach {
            popup.menu.add(it)
        }
        popup.gravity = Gravity.START
        popup.menu.setGroupCheckable(1, true, true)
        popup.setOnMenuItemClickListener { item ->
            viewModel.event(
                RestaurantsEvent.SortBy(
                    SortType.typeFromIndexList(
                        sortList.indexOf(item.title)
                    )
                )
            )
            true
        }
        popup.show()
    }

    override fun setupUiListener() {
        adapter.apply {
            onItemClickedListener = {
                findNavController().navigate(
                    RestaurantsFragmentDirections.actionRestaurantsFragmentToDetailFragment(it)
                )
            }

            onFavoriteClickedListener = { item, selected ->
                viewModel.event(RestaurantsEvent.Favorite(item, selected))
            }
        }
    }

    override fun setupObservers() {
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is RestaurantsState.Restaurants -> adapter.setItems(it.restaurants)
                else -> Unit
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvRestaurant.adapter = adapter
        binding.rvRestaurant.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = null
        }
    }
}