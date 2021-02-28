package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("dt") val date: Long,
    @SerializedName("coord") val coordinates: CityCoordinates,
    @SerializedName("main") val main: CityWeatherInfo,
    @SerializedName("sys") val country: CityCountry,
    @SerializedName("clouds") val clouds: CityClouds,
    @SerializedName("wind") val wind: CityWind,
    @SerializedName("weather") val weather: List<CityWeather>
)

