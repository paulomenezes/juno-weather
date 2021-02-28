package com.juno.weather.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name  = "id")
    var id: Long,

    @ColumnInfo(name = "city_name")
    var cityName: String
)