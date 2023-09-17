package ir.pooriak.restaurant.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.pooriak.core.base.GenericResponse
import ir.pooriak.restaurant.data.database.entity.RestaurantEntity
import ir.pooriak.restaurant.data.remote.entity.RestaurantsData
import ir.pooriak.restaurant.domain.model.Restaurant

/**
 * Created by POORIAK on 12,September,2023
 */
interface RestaurantRepository {
    fun restaurants(): Single<List<RestaurantEntity>>
    fun favorite(restaurant: Restaurant): Completable
}