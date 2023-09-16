package ir.pooriak.restaurant.di

import ir.pooriak.restaurant.presentation.feature.restaurants.adapter.RestaurantsAdapter
import org.koin.dsl.module

/**
 * Created by POORIAK on 15,September,2023
 */
val adapterModule = module {
    factory { RestaurantsAdapter() }
}