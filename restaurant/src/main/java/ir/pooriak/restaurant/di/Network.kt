package ir.pooriak.restaurant.di

import ir.pooriak.restaurant.data.remote.base.RestaurantApi
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Created by POORIAK on 15,September,2023
 */
val networkModule = module {
    /**
     * providing ApiServices
     */
    factory { provideRestaurantService(retrofit = get()) }
}

private fun provideRestaurantService(retrofit: Retrofit): RestaurantApi =
    retrofit.create(RestaurantApi::class.java)