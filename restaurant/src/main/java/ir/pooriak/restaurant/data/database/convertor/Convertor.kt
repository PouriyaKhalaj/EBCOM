package ir.pooriak.restaurant.data.database.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import ir.pooriak.restaurant.data.database.entity.SortingEntity
import java.util.Date

/**
 * Created by POORIAK on 16,September,2023
 */
internal class Converters {
    @TypeConverter
    fun sortingToString(value: SortingEntity?): String? =
        Gson().toJson(value)

    @TypeConverter
    fun stringToSorting(value: String?): SortingEntity? =
        Gson().fromJson(value, SortingEntity::class.java)
}