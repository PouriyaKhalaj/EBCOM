package ir.pooriak.restaurant.presentation.feature.restaurants.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.presentation.feature.restaurants.viewholder.RestaurantViewHolder

/**
 * Created by POORIAK on 15,September,2023
 */
class RestaurantsAdapter : RecyclerView.Adapter<RestaurantViewHolder>() {
    private var items: ArrayList<Restaurant> = arrayListOf()
    var onItemClickedListener: ((Restaurant) -> Unit)? = null
    var onFavoriteClickedListener: ((Restaurant, Boolean) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) =
        holder.bind(items[position])

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        return RestaurantViewHolder.create(
            parent = parent,
            onItemClickedListener = onItemClickedListener,
            onFavoriteClickedListener = { restaurant: Restaurant, favorite: Boolean, position: Int ->
                items[position] = restaurant.apply {
                    this.favorite = favorite
                }
                notifyItemChanged(position)
                onFavoriteClickedListener?.invoke(restaurant, favorite)
            }
        )
    }

    fun setItems(items: List<Restaurant>) {
        this.items = ArrayList(items)
        notifyDataSetChanged()
    }
}