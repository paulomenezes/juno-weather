package com.juno.weather.data.remote

import com.juno.weather.data.remote.models.city.City
import com.juno.weather.data.remote.models.FindResult
import com.juno.weather.data.remote.models.forecast.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("/data/2.5/find")
    fun findCity(
        @Query("q") cityName: String,
        @Query("units") unit: String,
        @Query("lang") language: String,
        @Query("appId") appId: String
    ): Call<FindResult<City>>

    @GET("/data/2.5/forecast")
    fun getForecast(
        @Query("id") id: Int,
        @Query("units") unit: String,
        @Query("lang") language: String,
        @Query("appId") appId: String
    ): Call<FindResult<Forecast>>
}