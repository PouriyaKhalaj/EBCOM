package ir.pooriak.restaurant.domain.repository

import io.reactivex.rxjava3.core.Single
import ir.pooriak.core.base.GenericResponse
import ir.pooriak.restaurant.data.remote.entity.RestaurantsData

/**
 * Created by POORIAK on 12,September,2023
 */
interface RestaurantRepository {
    fun restaurants(): Single<GenericResponse<RestaurantsData>>

    fun restaurantsSample(): Single<RestaurantsData>
}