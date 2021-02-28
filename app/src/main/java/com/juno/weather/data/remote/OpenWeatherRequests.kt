package com.juno.weather.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherRequests {
    private val instance = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = instance.create(OpenWeatherService::class.java)

    fun getOpenWeatherService(): OpenWeatherService = service
}