package com.juno.weather.data.remote.models.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("dt") val date: Long?,
    @SerializedName("coord") val coordinates: CityCoordinates?,
    @SerializedName("main") val main: CityWeatherInfo,
    @SerializedName("sys") val country: CityCountry,
    @SerializedName("clouds") val clouds: CityClouds,
    @SerializedName("wind") val wind: CityWind,
    @SerializedName("weather") val weather: List<CityWeather>
) : Parcelable

