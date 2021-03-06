package com.juno.weather.data.remote.models.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeatherInfo(
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feelsLike: Float?,
    @SerializedName("temp_min") val temperatureMin: Float?,
    @SerializedName("temp_max") val temperatureMax: Float?,
    @SerializedName("pressure") val pressure: Float?,
    @SerializedName("humidity") val humidity: Float?,
) : Parcelable