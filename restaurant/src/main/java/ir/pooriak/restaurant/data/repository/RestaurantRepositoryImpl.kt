package ir.pooriak.restaurant.data.repository

import io.reactivex.rxjava3.core.Single
import ir.pooriak.core.base.GenericResponse
import ir.pooriak.restaurant.data.database.dao.RestaurantDao
import ir.pooriak.restaurant.data.remote.base.RestaurantApi
import ir.pooriak.restaurant.data.remote.entity.RestaurantsData
import ir.pooriak.restaurant.domain.repository.RestaurantRepository

/**
 * Created by POORIAK on 13,September,2023
 */
internal class RestaurantRepositoryImpl(
    private val apiService: RestaurantApi,
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {
    override fun restaurants(): Single<GenericResponse<RestaurantsData>> =
        apiService.getRestaurant()

    override fun restaurantsSample(): Single<RestaurantsData> =
        apiService.getRestaurantSample()
}