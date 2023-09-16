package ir.pooriak.restaurant.data.remote.base

import io.reactivex.rxjava3.core.Single
import ir.pooriak.core.base.GenericResponse
import ir.pooriak.restaurant.data.remote.entity.RestaurantsData
import ir.pooriak.restaurant.domain.model.Restaurants
import retrofit2.http.GET

/**
 * Created by POORIAK on 12,September,2023
 */
interface RestaurantApi {
    //    base url https://run.mocky.io/v3
    @GET("3f5f77e7-db1d-454b-8230-302a9bc1fe8c")
    fun getRestaurant(): Single<GenericResponse<RestaurantsData>>

    @GET("3f5f77e7-db1d-454b-8230-302a9bc1fe8c")
    fun getRestaurantSample(): Single<RestaurantsData>
}
