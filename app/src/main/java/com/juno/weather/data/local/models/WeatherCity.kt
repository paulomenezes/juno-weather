package com.juno.weather.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather_city")
data class WeatherCity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name  = "id")
    var id: Long = 0,

    @ColumnInfo(name = "weather_request_id")
    var weatherRequestId: Long,

    @ColumnInfo(name = "icon")
    var icon: String,

    @ColumnInfo(name  = "city_id")
    var cityId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "temperature")
    var temperature: Float,

    @ColumnInfo(name = "clouds")
    var clouds: Int,

    @ColumnInfo(name = "wind_speed")
    var windSpeed: Float
)