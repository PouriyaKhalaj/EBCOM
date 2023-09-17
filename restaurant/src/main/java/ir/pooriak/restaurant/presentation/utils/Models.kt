package ir.pooriak.restaurant.presentation.utils

import androidx.annotation.StringRes
import ir.pooriak.restaurant.R

/**
 * Created by POORIAK on 17,September,2023
 */
enum class SortType(@StringRes val value: Int) {
    BEST_MATCH(R.string.best_match),
    RATING_AVERAGE(R.string.rating_average),
    NEWEST(R.string.newest),
    DISTANCE(R.string.distance),
    POPULARITY(R.string.popularity),
    AVERAGE_PRODUCT_PRICE(R.string.average_product_price),
    DELIVERY_COSTS(R.string.delivery_costs),
    MIN_COST(R.string.min_cost);

    companion object {
        fun filterList() = SortType.values().toList()
        fun typeFromIndexList(index: Int) = filterList()[index]
    }
}