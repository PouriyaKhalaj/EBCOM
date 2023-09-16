package ir.pooriak.restaurant.presentation.feature.restaurants.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.presentation.feature.restaurants.viewholder.RestaurantViewHolder

/**
 * Created by POORIAK on 15,September,2023
 */
class RestaurantsAdapter : RecyclerView.Adapter<RestaurantViewHolder>() {
    private var items: List<Restaurant> = emptyList()
    var onItemClickedListener: ((Restaurant) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) =
        holder.bind(items[position])

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        return RestaurantViewHolder.create(
            parent,
            onItemClickedListener
        )
    }

    fun setItems(items: List<Restaurant>) {
        this.items = items
        notifyDataSetChanged()
    }
}