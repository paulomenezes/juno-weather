package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class CityWind(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val degrees: Float
)