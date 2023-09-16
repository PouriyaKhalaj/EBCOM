package ir.pooriak.restaurant.data.remote.entity

import ir.pooriak.core.base.Mapper
import ir.pooriak.restaurant.domain.model.Restaurants

/**
 * Created by POORIAK on 13,September,2023
 */
class RestaurantMapper : Mapper<Restaurants, RestaurantsData> {
    override fun dataToDomainModel(input: RestaurantsData): Restaurants =
        input.toDomainModel()
}