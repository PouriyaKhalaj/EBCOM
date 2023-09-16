package ir.pooriak.restaurant.di

import androidx.paging.ExperimentalPagingApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by POORIAK on 15,September,2023
 */
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
val restaurantModuleList = listOf(
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
    mapperModule,
    adapterModule,
    databaseModule,
//    dataSourceModule
)