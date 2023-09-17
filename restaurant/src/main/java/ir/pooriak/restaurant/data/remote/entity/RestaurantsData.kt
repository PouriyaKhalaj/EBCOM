package ir.pooriak.restaurant.data.remote.entity

import com.google.gson.annotations.SerializedName
import ir.pooriak.core.base.DataModel
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.Restaurants
import ir.pooriak.restaurant.domain.model.SortingDetail
import ir.pooriak.restaurant.domain.model.Status

/**
 * Created by POORIAK on 12,September,2023
 */

data class RestaurantsData(
    @SerializedName("restaurants") val restaurants: MutableList<RestaurantData>
) : DataModel {
    override fun toDomainModel(): Restaurants = Restaurants(restaurants.map { it.toDomainModel() })
}

data class RestaurantData(
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("sortingValues") val sortingValues: SortingValuesData
) : DataModel {
    override fun toDomainModel(): Restaurant = Restaurant(
        id = 0,
        favorite = false,
        name = name,
        status = Status.fromValue(status),
        sortingValues = sortingValues.toDomainModel()
    )
}

data class SortingValuesData(
    @SerializedName("bestMatch") val bestMatch: Float,
    @SerializedName("newest") val newest: Float,
    @SerializedName("ratingAverage") val ratingAverage: Float,
    @SerializedName("distance") val distance: Int,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("averageProductPrice") val averageProductPrice: Int,
    @SerializedName("deliveryCosts") val deliveryCosts: Int,
    @SerializedName("minCost") val minCost: Int,
) : DataModel {
    override fun toDomainModel(): SortingDetail = SortingDetail(
        bestMatch = bestMatch,
        newest = newest,
        ratingAverage = ratingAverage,
        distance = distance,
        popularity = popularity,
        averageProductPrice = averageProductPrice,
        deliveryCosts = deliveryCosts,
        minCost = minCost
    )
}