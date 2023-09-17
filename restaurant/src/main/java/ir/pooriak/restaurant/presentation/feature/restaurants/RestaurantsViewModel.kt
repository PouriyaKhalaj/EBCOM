package ir.pooriak.restaurant.presentation.feature.restaurants

import ir.pooriak.core.usecase.UseCaseState
import ir.pooriak.core.viewmodel.BaseViewModel
import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCase
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.Status

/**
 * Created by POORIAK on 13,September,2023
 */
class RestaurantsViewModel(private val restaurantUseCase: RestaurantUseCase) :
    BaseViewModel<RestaurantsState, RestaurantsEvent>() {

    private var restaurants: MutableList<Restaurant> = mutableListOf()

    override fun onEvent(event: RestaurantsEvent) {
        when (event) {
            RestaurantsEvent.Restaurants -> getRestaurants()
            is RestaurantsEvent.Favorite -> favorite(event.restaurant, event.selected)
            is RestaurantsEvent.SortBy -> sortByPosition(event.position)
        }
    }

    private fun sortByPosition(position: Int) {
        restaurants.sortWith(compareBy(
            { it.favorite },
            { it.status == Status.OPEN },
            { it.status == Status.ORDER_AHEAD },
            { it.status == Status.CLOSED },
            {
                when (position) {
                    1 -> it.sortingValues.ratingAverage
                    2 -> it.sortingValues.newest
                    3 -> it.sortingValues.distance.toFloat()
                    4 -> it.sortingValues.popularity
                    5 -> it.sortingValues.averageProductPrice.toFloat()
                    6 -> it.sortingValues.deliveryCosts.toFloat()
                    7 -> it.sortingValues.minCost.toFloat()
                    else -> it.sortingValues.bestMatch
                }
            }
        ))

        state.postValue(RestaurantsState.Restaurants(restaurants.reversed()))
    }

    private fun favorite(restaurant: Restaurant, selected: Boolean) {
        restaurantUseCase.favorite(restaurant.apply {
            favorite = selected
        })
    }

    private fun getRestaurants() {
        restaurantUseCase.restaurants {
            when (it) {
                is UseCaseState.Success -> {
                    restaurants = it.data.restaurants.toMutableList()
                    sortByPosition(0)
                }

                else -> Unit
            }
        }
    }
}