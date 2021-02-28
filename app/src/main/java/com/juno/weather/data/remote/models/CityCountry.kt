package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class CityCountry(
    @SerializedName("country") val name: String,
)