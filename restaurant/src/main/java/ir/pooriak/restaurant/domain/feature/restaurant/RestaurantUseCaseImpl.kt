package ir.pooriak.restaurant.domain.feature.restaurant

import android.annotation.SuppressLint
import ir.pooriak.core.usecase.DomainResult
import ir.pooriak.core.usecase.SchedulerProvider
import ir.pooriak.core.usecase.UseCaseState
import ir.pooriak.restaurant.data.remote.entity.RestaurantMapper
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.Restaurants
import ir.pooriak.restaurant.domain.repository.RestaurantRepository

/**
 * Created by POORIAK on 13,September,2023
 */
internal class RestaurantUseCaseImpl(
    private val schedulerProvider: SchedulerProvider,
    private val restaurantMapper: RestaurantMapper,
    private val restaurantRepository: RestaurantRepository,
) : RestaurantUseCase {

    @SuppressLint("CheckResult")
    override fun restaurants(result: DomainResult<Restaurants>) {
        result(UseCaseState.Loading())
        restaurantRepository.restaurants().subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.mainThread).subscribe { response, throwable ->
                response?.let { restaurantEntities ->
                    result(UseCaseState.Success(Restaurants(restaurantEntities.map { it.toDomainModel() })))
                }
            }
    }

    override fun favorite(restaurant: Restaurant) {
        restaurantRepository.favorite(restaurant).subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.mainThread).subscribe()
    }
}