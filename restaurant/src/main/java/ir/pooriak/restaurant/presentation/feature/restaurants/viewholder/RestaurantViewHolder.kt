package ir.pooriak.restaurant.presentation.feature.restaurants.viewholder

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pooriak.restaurant.R
import ir.pooriak.restaurant.databinding.RowRestaurantBinding
import ir.pooriak.restaurant.domain.model.Restaurant

/**
 * Created by POORIAK on 15,September,2023
 */
class RestaurantViewHolder(
    private val binding: RowRestaurantBinding,
    private val onItemClickedListener: ((Restaurant) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: Restaurant

    init {
        binding.apply {
            root.setOnClickListener {
                onItemClickedListener?.let { it(currentItem) }
            }
        }
    }

    fun bind(item: Restaurant) {
        currentItem = item

        binding.apply {
            name.text = titleSpannableString(
                binding.root.context.getString(R.string.title_name),
                item.name
            )

            status.text = titleSpannableString(
                binding.root.context.getString(R.string.title_status),
                item.status
            )

            point.text = item.sortingValues.ratingAverage.toString()
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
            onItemClickedListener: ((Restaurant) -> Unit)?
        ): RestaurantViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_restaurant, parent, false)

            val binding = RowRestaurantBinding.bind(view)

            return RestaurantViewHolder(
                binding,
                onItemClickedListener
            )
        }
    }
}