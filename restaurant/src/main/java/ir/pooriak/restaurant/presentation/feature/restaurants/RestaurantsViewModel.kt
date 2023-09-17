package ir.pooriak.restaurant.presentation.feature.restaurants

import ir.pooriak.core.usecase.UseCaseState
import ir.pooriak.core.viewmodel.BaseViewModel
import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCase
import ir.pooriak.restaurant.domain.model.Restaurant

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
        restaurants.sortByDescending { item ->
            when (position) {
                1 -> item.sortingValues.bestMatch
                2 -> item.sortingValues.newest
                3 -> item.sortingValues.distance.toFloat()
                4 -> item.sortingValues.popularity
                5 -> item.sortingValues.averageProductPrice.toFloat()
                6 -> item.sortingValues.deliveryCosts.toFloat()
                7 -> item.sortingValues.minCost.toFloat()
                else -> item.sortingValues.ratingAverage
            }
        }

        state.postValue(RestaurantsState.Restaurants(restaurants))
    }

    private fun favorite(restaurant: Restaurant, selected: Boolean) {
        restaurantUseCase.favorite(restaurant.apply {
            favorite = selected
        })
    }

    private fun getRestaurants() {
        restaurantUseCase.restaurants {
            when (it) {
//                is UseCaseState.ApiError -> TODO()
//                is UseCaseState.Error -> TODO()
//                is UseCaseState.Loading -> TODO()
//                is UseCaseState.NetworkError -> TODO()
                is UseCaseState.Success -> {
                    restaurants = it.data.restaurants.toMutableList()
                    state.postValue(RestaurantsState.Restaurants(restaurants.apply {
                        this@apply.sortByDescending { item -> item.sortingValues.ratingAverage }
                    }))
                }

                else -> Unit
            }
        }
    }
}