package ir.pooriak.restaurant.presentation.feature.detail

import ir.pooriak.core.viewmodel.BaseViewModel
import ir.pooriak.restaurant.domain.feature.restaurant.RestaurantUseCase
import ir.pooriak.restaurant.domain.model.Restaurant

/**
 * Created by POORIAK on 17,September,2023
 */
class DetailViewModel(private val restaurantUseCase: RestaurantUseCase) :
    BaseViewModel<DetailState, DetailEvent>() {

    lateinit var restaurant: Restaurant
    override fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.Favorite -> favorite(restaurant)
            is DetailEvent.ReceiveRestaurant -> {
                this.restaurant = event.restaurant
                showRestaurantDetail(event.restaurant)
            }
        }
    }

    private fun showRestaurantDetail(restaurant: Restaurant) {
        state.postValue(DetailState.ShowRestaurant(restaurant))
    }

    private fun favorite(item: Restaurant) {
        restaurant.apply {
            favorite = !item.favorite
        }
        restaurantUseCase.favorite(restaurant)
        state.postValue(DetailState.UpdateFavorite(restaurant))
    }
}