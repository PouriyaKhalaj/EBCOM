package ir.pooriak.restaurant.di

import ir.pooriak.restaurant.presentation.feature.restaurants.RestaurantsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by POORIAK on 15,September,2023
 */
@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { RestaurantsViewModel(get()) }
}