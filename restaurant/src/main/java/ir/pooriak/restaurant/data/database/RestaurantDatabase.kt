package ir.pooriak.restaurant.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.pooriak.restaurant.data.database.convertor.Converters
import ir.pooriak.restaurant.data.database.dao.RestaurantDao
import ir.pooriak.restaurant.data.database.entity.RestaurantEntity

/**
 * Created by POORIAK on 15,September,2023
 */

@Database(
    entities = [RestaurantEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getInstance(context: Context): RestaurantDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RestaurantDatabase::class.java, "restaurant-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}