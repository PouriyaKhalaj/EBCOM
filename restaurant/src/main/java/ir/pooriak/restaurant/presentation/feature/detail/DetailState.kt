package ir.pooriak.restaurant.presentation.feature.detail

import ir.pooriak.core.viewmodel.BaseState
import ir.pooriak.core.viewmodel.BaseViewModelEvent
import ir.pooriak.restaurant.domain.model.Restaurant

/**
 * Created by POORIAK on 17,September,2023
 */
sealed class DetailState : BaseState {
    data class ShowRestaurant(val restaurant: Restaurant) : DetailState()
    data class UpdateFavorite(val restaurant: Restaurant) : DetailState()
}

sealed class DetailEvent : BaseViewModelEvent {
    data class ReceiveRestaurant(val restaurant: Restaurant) : DetailEvent()
    data object Favorite : DetailEvent()
}