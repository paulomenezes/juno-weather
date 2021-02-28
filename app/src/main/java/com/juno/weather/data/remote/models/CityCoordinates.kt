package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class CityCoordinates(
    @SerializedName("lat") val latitude: Float,
    @SerializedName("lon") val longitude: Float
)