package ir.pooriak.restaurant.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ir.pooriak.core.view.adapter.titleValue.TitleValue
import ir.pooriak.core.view.adapter.titleValue.TitleValueAdapter
import ir.pooriak.core.view.fragment.BaseFragment
import ir.pooriak.restaurant.R
import ir.pooriak.restaurant.databinding.FragmentDetailBinding
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.SortingDetail
import ir.pooriak.restaurant.presentation.utils.toSpannableString
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : BaseFragment() {
    private var _mBinding: FragmentDetailBinding? = null
    private val binding get() = _mBinding!!

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()
    private val adapter by lazy {
        TitleValueAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        toolbarTitle(args.restaurant.name)
        binding.rvSorting.adapter = adapter
        viewModel.event(DetailEvent.ReceiveRestaurant(args.restaurant))

    }

    override fun setupUiListener() {
        binding.icFavorite.setOnClickListener {
            viewModel.event(DetailEvent.Favorite)
        }
    }

    override fun setupObservers() {
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is DetailState.ShowRestaurant -> showRestaurantDetail(it.restaurant)
                is DetailState.UpdateFavorite -> setFavoriteIcon(it.restaurant.favorite)
            }
        }
    }

    private fun showRestaurantDetail(item: Restaurant) {
        with(binding) {
            name.text = item.name
            point.text = item.sortingValues.ratingAverage.toString()
            status.text = item.status.toSpannableString(requireContext())
            setFavoriteIcon(item.favorite)
            fillAdapter(item.sortingValues)
        }
    }

    private fun setFavoriteIcon(favorite: Boolean) {
        AppCompatResources.getDrawable(
            binding.root.context, if (favorite) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )?.let {
            binding.icFavorite.setImageDrawable(it)
        }
    }

    private fun fillAdapter(model: SortingDetail) {
        listOf(
            TitleValue.StringStyle(
                title = getString(R.string.best_match),
                value = "${model.bestMatch}"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.newest),
                value = "${model.newest}"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.rating_average),
                value = "${model.ratingAverage}"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.distance),
                value = "${NumberFormat.getIntegerInstance().format(model.distance.toLong())}"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.popularity),
                value = "${model.popularity}"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.average_product_price),
                value = "${
                    NumberFormat.getIntegerInstance().format(model.averageProductPrice.toLong())
                } $"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.delivery_costs),
                value = "${
                    NumberFormat.getIntegerInstance().format(model.deliveryCosts.toLong())
                } $"
            ),
            TitleValue.StringStyle(
                title = getString(R.string.min_cost),
                value = "${NumberFormat.getIntegerInstance().format(model.minCost.toLong())}"
            ),
        ).let {
            adapter.addItems(it)
        }
    }
}