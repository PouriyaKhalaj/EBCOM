package ir.pooriak.restaurant.domain.model

import android.os.Parcelable
import ir.pooriak.core.base.DomainModel
import kotlinx.parcelize.Parcelize

/**
 * Created by POORIAK on 13,September,2023
 */

data class Restaurants(val restaurants: List<Restaurant>) : DomainModel

@Parcelize
data class Restaurant(
    val id: Long,
    val name: String,
    val status: String,
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
