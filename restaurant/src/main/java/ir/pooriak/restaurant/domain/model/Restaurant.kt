package ir.pooriak.restaurant.domain.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import ir.pooriak.core.base.DomainModel
import ir.pooriak.restaurant.R
import kotlinx.parcelize.Parcelize

/**
 * Created by POORIAK on 13,September,2023
 */

data class Restaurants(val restaurants: List<Restaurant>) : DomainModel

@Parcelize
data class Restaurant(
    val id: Long,
    val name: String,
    val status: Status?,
    var favorite: Boolean,
    val sortingValues: SortingDetail
) : DomainModel, Parcelable

@Parcelize
data class SortingDetail(
    val bestMatch: Float,
    val newest: Float,
    val ratingAverage: Float,
    val distance: Int,
    val popularity: Float,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int,
) : DomainModel, Parcelable

enum class Status(val value: String, val show: String, @ColorRes val color: Int) {
    OPEN(value = "open", show = "Open", color = R.color.green_400),
    CLOSED(value = "closed", show = "Closed", color = R.color.red_400),
    ORDER_AHEAD(value = "order ahead", show = "Order Ahead", color = R.color.orange_400);

    companion object {
        fun fromValue(value: String): Status? =
            Status.values().firstOrNull { it.value == value }
    }
}
