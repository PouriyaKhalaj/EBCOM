package ir.pooriak.restaurant.di

import ir.pooriak.restaurant.data.repository.RestaurantRepositoryImpl
import ir.pooriak.restaurant.domain.repository.RestaurantRepository
import org.koin.dsl.module

/**
 * Created by POORIAK on 15,September,2023
 */
val repositoryModule = module {
    factory<RestaurantRepository> { RestaurantRepositoryImpl(get(), get()) }
}