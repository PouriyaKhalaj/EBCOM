package ir.pooriak.restaurant.presentation.feature.restaurants

import ir.pooriak.core.usecase.UseCaseState
import ir.pooriak.core.viewmodel.BaseViewModel
import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCase
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.model.Status
import ir.pooriak.restaurant.presentation.utils.SortType

/**
 * Created by POORIAK on 13,September,2023
 */
class RestaurantsViewModel(private val restaurantUseCase: RestaurantUseCase) :
    BaseViewModel<RestaurantsState, RestaurantsEvent>() {

    private var restaurants: MutableList<Restaurant> = mutableListOf()
    private lateinit var currentSortFilter: SortType

    override fun onEvent(event: RestaurantsEvent) {
        when (event) {
            RestaurantsEvent.Restaurants -> {
                if (!::currentSortFilter.isInitialized)
                    getRestaurants()
            }

            is RestaurantsEvent.Favorite -> favorite(event.restaurant, event.selected)
            is RestaurantsEvent.SortBy -> sortBySortType(event.sortFilter)
        }
    }

    private fun sortBySortType(sortFilter: SortType) {
        currentSortFilter = sortFilter

        restaurants.sortWith(compareBy<Restaurant>(
            { it.favorite },
            { it.status == Status.OPEN },
            { it.status == Status.ORDER_AHEAD },
            { it.status == Status.CLOSED }
        ).thenBy {
            when (sortFilter) {
                SortType.BEST_MATCH -> it.sortingValues.bestMatch
                SortType.NEWEST -> it.sortingValues.newest
                SortType.RATING_AVERAGE -> it.sortingValues.ratingAverage
                SortType.DISTANCE -> it.sortingValues.distance.toFloat()
                SortType.POPULARITY -> it.sortingValues.popularity
                SortType.AVERAGE_PRODUCT_PRICE -> it.sortingValues.averageProductPrice.toFloat()
                SortType.DELIVERY_COSTS -> it.sortingValues.deliveryCosts.toFloat()
                SortType.MIN_COST -> it.sortingValues.minCost.toFloat()
            }
        })

        state.postValue(RestaurantsState.Restaurants(restaurants.reversed()))
    }

    private fun favorite(restaurant: Restaurant, selected: Boolean) {
        restaurantUseCase.favorite(restaurant.apply {
            favorite = selected
        })
        sortBySortType(currentSortFilter)
    }

    private fun getRestaurants() {
        restaurantUseCase.restaurants {
            when (it) {
                is UseCaseState.Success -> {
                    restaurants = it.data.restaurants.toMutableList()
                    sortBySortType(SortType.BEST_MATCH)
                }

                else -> Unit
            }
        }
    }
}