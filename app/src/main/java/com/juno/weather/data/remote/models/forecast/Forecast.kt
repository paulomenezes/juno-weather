package com.juno.weather.data.remote.models.forecast

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.juno.weather.data.remote.models.city.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    @SerializedName("dt") val date: Long,
    @SerializedName("coord") val coordinates: CityCoordinates?,
    @SerializedName("main") val main: CityWeatherInfo,
    @SerializedName("sys") val country: CityCountry,
    @SerializedName("clouds") val clouds: CityClouds,
    @SerializedName("wind") val wind: CityWind,
    @SerializedName("weather") val weather: List<CityWeather>
) : Parcelable