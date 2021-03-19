package com.juno.weather.data.local.daos

import androidx.room.*
import com.juno.weather.data.local.models.Favorite

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite): Long

    @Update
    fun update(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM favorites ORDER BY city_name, country ASC")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE city_name LIKE :text OR country LIKE :text ORDER BY city_name, country ASC")
    fun getByNameCountry(text: String): List<Favorite>

    @Query("SELECT * FROM favorites WHERE city_id = :cityId")
    fun getById(cityId: Int): Favorite?
}