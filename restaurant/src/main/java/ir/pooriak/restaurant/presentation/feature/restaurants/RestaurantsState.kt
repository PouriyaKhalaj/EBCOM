package ir.pooriak.restaurant.presentation.feature.restaurants

import ir.pooriak.core.base.ApiError
import ir.pooriak.core.base.NetworkErrorDetail
import ir.pooriak.core.viewmodel.BaseState
import ir.pooriak.core.viewmodel.BaseViewModelEvent
import ir.pooriak.restaurant.domain.model.Restaurant

/**
 * Created by POORIAK on 13,September,2023
 */
sealed class RestaurantsState : BaseState {
    data object Loading : RestaurantsState()
    data object ShowShimmer : RestaurantsState()
    data object CheckEmptyState : RestaurantsState()
    data class Error(val error: Throwable) : RestaurantsState()
    data class RestaurantsApiError(val apiError: ApiError) : RestaurantsState()
    data class NetworkError(val networkError: NetworkErrorDetail) : RestaurantsState()
    data class Restaurants(val restaurants: List<Restaurant>) : RestaurantsState()
}

sealed class RestaurantsEvent : BaseViewModelEvent {
    data object Restaurants : RestaurantsEvent()
}