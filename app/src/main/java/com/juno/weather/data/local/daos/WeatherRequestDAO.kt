package com.juno.weather.data.local.daos

import androidx.room.*
import com.juno.weather.data.local.models.WeatherCity
import com.juno.weather.data.local.models.WeatherRequest
import com.juno.weather.data.local.models.WeatherRequestWithCities

@Dao
interface WeatherRequestDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherRequest: WeatherRequest): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(weatherCity: WeatherCity): Long

    @Transaction
    @Query("SELECT * FROM weather_request WHERE city_name = :cityName AND unit = :unit AND language = :language ORDER BY date DESC LIMIT 1")
    fun getWeatherRequest(cityName: String, unit: String, language: String): WeatherRequestWithCities?
}