package ir.pooriak.restaurant.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.pooriak.core.base.EntityModel
import ir.pooriak.restaurant.data.remote.entity.RestaurantData
import ir.pooriak.restaurant.data.remote.entity.SortingValuesData
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.SortingDetail

/**
 * Created by POORIAK on 15,September,2023
 */
@Entity(tableName = "restaurant")
data class RestaurantEntity(
    val name: String,
    val status: String,
    val sorting: SortingEntity,
    var favorite: Boolean = false
) : EntityModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entity_id")
    var entityId: Long = 0
    override fun toDomainModel(): Restaurant =
        Restaurant(
            id = entityId,
            favorite = favorite,
            name = name,
            status = status,
            sortingValues = sorting.toDomainModel()
        )
}

@Entity(tableName = "sorting")
data class SortingEntity(
    @ColumnInfo(name = "best_match")
    val bestMatch: Float,
    val newest: Float,
    @ColumnInfo(name = "rating_average")
    val ratingAverage: Float,
    val distance: Int,
    val popularity: Float,
    @ColumnInfo(name = "average_product_price")
    val averageProductPrice: Int,
    @ColumnInfo(name = "delivery_costs")
    val deliveryCosts: Int,
    @ColumnInfo(name = "min_cost")
    val minCost: Int,
) : EntityModel {
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

internal fun RestaurantData.toEntityModel() =
    RestaurantEntity(
        name = name,
        status = status,
        sorting = sortingValues.toEntityModel()
    )

internal fun SortingValuesData.toEntityModel() =
    SortingEntity(
        bestMatch = bestMatch,
        newest = newest,
        ratingAverage = ratingAverage,
        distance = distance,
        popularity = popularity,
        averageProductPrice = averageProductPrice,
        deliveryCosts = deliveryCosts,
        minCost = minCost
    )