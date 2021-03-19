package com.juno.weather.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherRequestWithCities(
    @Embedded
    val request: WeatherRequest,

    @Relation(
        parentColumn = "id",
        entityColumn = "weather_request_id"
    )
    val cities: List<WeatherCity>
)