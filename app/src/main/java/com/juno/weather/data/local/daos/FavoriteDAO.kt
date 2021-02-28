package com.juno.weather.data.local.daos

import androidx.room.*
import com.juno.weather.data.local.models.Favorite

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite): Long

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getById(id: Long): Favorite
}