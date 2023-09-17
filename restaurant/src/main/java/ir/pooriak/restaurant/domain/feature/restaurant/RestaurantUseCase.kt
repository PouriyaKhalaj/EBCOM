package ir.pooriak.restaurant.domain.feature.restaurant

import ir.pooriak.core.usecase.DomainResult
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.Restaurants

/**
 * Created by POORIAK on 13,September,2023
 */

interface RestaurantUseCase {
    fun restaurants(result: DomainResult<Restaurants>)
    fun favorite(restaurant: Restaurant)
}