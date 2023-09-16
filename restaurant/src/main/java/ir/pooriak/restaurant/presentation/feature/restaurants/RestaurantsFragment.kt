package ir.pooriak.restaurant.presentation.feature.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pooriak.core.view.fragment.BaseFragment
import ir.pooriak.restaurant.R
import ir.pooriak.restaurant.databinding.FragmentRestaurantsBinding
import ir.pooriak.restaurant.presentation.feature.restaurants.adapter.RestaurantsAdapter
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        viewModel.event(RestaurantsEvent.Restaurants)
        return binding.root
    }

    override fun setupView() {
        toolbarTitle(getString(R.string.restaurants))
        setupRecyclerView()
    }

    override fun setupUiListener() {
        adapter.onItemClickedListener = {
            findNavController().navigate(
                RestaurantsFragmentDirections.actionRestaurantsFragmentToDetailFragment(it)
            )
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