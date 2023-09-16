package ir.pooriak.restaurant.di

import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCase
import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCaseImpl
import org.koin.dsl.module

/**
 * Created by POORIAK on 15,September,2023
 */
val useCaseModule = module {
    factory<RestaurantUseCase> { RestaurantUseCaseImpl(get(), get(), get()) }
}