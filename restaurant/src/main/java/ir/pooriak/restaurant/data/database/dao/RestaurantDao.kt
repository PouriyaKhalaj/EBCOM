package ir.pooriak.restaurant.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ir.pooriak.core.base.BaseDao
import ir.pooriak.restaurant.data.database.entity.RestaurantEntity

/**
 * Created by POORIAK on 15,September,2023
 */
@Dao
interface RestaurantDao : BaseDao<RestaurantEntity> {
    @Query("SELECT * FROM restaurant")
    fun restaurants(): Single<List<RestaurantEntity>>

    @Query("DELETE FROM restaurant WHERE entity_id =:restaurantId")
    fun delete(restaurantId: Long): Completable
}