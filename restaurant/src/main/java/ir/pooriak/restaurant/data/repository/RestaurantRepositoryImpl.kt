package ir.pooriak.restaurant.data.repository

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import ir.pooriak.core.base.repository.NetworkBoundResource
import ir.pooriak.restaurant.data.database.dao.RestaurantDao
import ir.pooriak.restaurant.data.database.entity.RestaurantEntity
import ir.pooriak.restaurant.data.database.entity.toEntityModel
import ir.pooriak.restaurant.data.remote.base.RestaurantApi
import ir.pooriak.restaurant.data.remote.entity.RestaurantsData
import ir.pooriak.restaurant.domain.model.Restaurant
import ir.pooriak.restaurant.domain.repository.RestaurantRepository

/**
 * Created by POORIAK on 13,September,2023
 */
internal class RestaurantRepositoryImpl(
    private val apiService: RestaurantApi,
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {
    override fun restaurants(): Single<List<RestaurantEntity>> =
        restaurantBound.asSingle()

    override fun favorite(restaurant: Restaurant): Completable =
        restaurantDao.update(restaurant.toEntityModel())


    private val restaurantBound =
        object : NetworkBoundResource<List<RestaurantEntity>, RestaurantsData>() {
            override fun processResult(response: RestaurantsData): List<RestaurantEntity> {
                val mappedResult = mutableListOf<RestaurantEntity>()
                response.restaurants.map {
                    it.toEntityModel()
                }.forEach { mappedResult.add(it) }
                return mappedResult
            }

            @SuppressLint("CheckResult")
            override fun saveCallResult(item: RestaurantsData) {
                restaurantDao.favorites().subscribe { favorites, _ ->
                    restaurantDao.deleteAll()
                        .subscribeWith(object : DisposableCompletableObserver() {
                            override fun onComplete() {
                                restaurantDao.insert(item.restaurants.map {
                                    it.toEntityModel().apply {
                                        favorites?.find { favorite ->
                                            favorite.name == name
                                        }?.let {
                                            this.favorite = true
                                        }
                                    }
                                }).subscribe()
                                dispose()
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                                dispose()
                            }
                        })
                }
            }

            override fun shouldFetch(): Boolean = true

            override fun loadFromDb(): Single<List<RestaurantEntity>> =
                restaurantDao.restaurants()

            override fun createCall(): Single<RestaurantsData> =
                apiService.getRestaurantSample()
        }
}