package com.juno.weather.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather_request")
data class WeatherRequest(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name  = "id")
    var id: Long = 0,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "city_name")
    var cityName: String,

    @ColumnInfo(name = "unit")
    var unit: String,

    @ColumnInfo(name = "language")
    var language: String
)