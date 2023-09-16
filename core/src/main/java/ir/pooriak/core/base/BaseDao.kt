package ir.pooriak.core.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable

/**
 * Created by POORIAK on 13,September,2023
 */
@Dao
interface BaseDao<T : EntityModel> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<T>): Completable

    @Update
    fun update(entity: T): Completable

    @Delete
    fun delete(entity: T): Completable
}