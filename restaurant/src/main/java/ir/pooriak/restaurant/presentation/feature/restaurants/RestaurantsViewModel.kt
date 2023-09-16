package ir.pooriak.restaurant.presentation.feature.restaurants

import ir.pooriak.core.usecase.UseCaseState
import ir.pooriak.core.viewmodel.BaseViewModel
import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCase

/**
 * Created by POORIAK on 13,September,2023
 */
class RestaurantsViewModel(private val restaurantUseCase: RestaurantUseCase) :
    BaseViewModel<RestaurantsState, RestaurantsEvent>() {

    override fun onEvent(event: RestaurantsEvent) {
        when (event) {
            RestaurantsEvent.Restaurants -> getRestaurants()
        }
    }

    private fun getRestaurants() {
        restaurantUseCase.restaurants {
            when (it) {
//                is UseCaseState.ApiError -> TODO()
//                is UseCaseState.Error -> TODO()
//                is UseCaseState.Loading -> TODO()
//                is UseCaseState.NetworkError -> TODO()
                is UseCaseState.Success -> state.postValue(RestaurantsState.Restaurants(it.data.restaurants))
                else ->Unit
            }
        }
    }
}