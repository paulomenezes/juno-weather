package com.juno.weather.data.remote

import com.juno.weather.data.remote.models.City
import com.juno.weather.data.remote.models.FindResult
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
}