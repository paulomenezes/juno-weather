package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class CityWeather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)