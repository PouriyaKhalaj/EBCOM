package ir.pooriak.restaurant.di

import androidx.room.Room
import ir.pooriak.restaurant.data.database.RestaurantDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by POORIAK on 15,September,2023
 */
val databaseModule = module {
    single {
        RestaurantDatabase.getInstance(androidApplication())
    }

    factory { (get() as RestaurantDatabase).restaurantDao() }
}