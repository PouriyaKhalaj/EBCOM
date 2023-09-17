package ir.pooriak.restaurant.presentation.feature.restaurants.viewholder

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import ir.pooriak.restaurant.R
import ir.pooriak.restaurant.databinding.RowRestaurantBinding
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.presentation.utils.toSpannableString

/**
 * Created by POORIAK on 15,September,2023
 */
class RestaurantViewHolder(
    private val binding: RowRestaurantBinding,
    private val onItemClickedListener: ((Restaurant) -> Unit)?,
    private val onFavoriteClickedListener: ((Restaurant, Boolean, Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: Restaurant

    init {
        binding.apply {
            root.setOnClickListener {
                onItemClickedListener?.let { it(currentItem) }
            }
            icFavorite.setOnClickListener {
                onFavoriteClickedListener?.let {
                    it(
                        currentItem, !currentItem.favorite, adapterPosition
                    )
                }
            }
        }
    }

    fun bind(item: Restaurant) {
        currentItem = item

        binding.apply {
            name.text = item.name
            status.text = item.status.toSpannableString(binding.root.context)
            point.text = item.sortingValues.ratingAverage.toString()

            AppCompatResources.getDrawable(
                binding.root.context, if (item.favorite) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )?.let {
                icFavorite.setImageDrawable(it)
            }
        }
    }

    private fun titleSpannableString(vararg values: String): SpannableString {
        var content = ""
        values.forEach { content += "$it " }
        return SpannableString(content)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClickedListener: ((Restaurant) -> Unit)?,
            onFavoriteClickedListener: ((Restaurant, Boolean, Int) -> Unit)?
        ): RestaurantViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.row_restaurant, parent, false)

            val binding = RowRestaurantBinding.bind(view)

            return RestaurantViewHolder(
                binding, onItemClickedListener, onFavoriteClickedListener
            )
        }
    }
}