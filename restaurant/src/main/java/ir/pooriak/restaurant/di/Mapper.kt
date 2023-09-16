package ir.pooriak.restaurant.di

import ir.pooriak.restaurant.data.remote.entity.RestaurantMapper
import org.koin.dsl.module

/**
 * Created by POORIAK on 15,September,2023
 */
val mapperModule = module {
    factory { RestaurantMapper() }
}