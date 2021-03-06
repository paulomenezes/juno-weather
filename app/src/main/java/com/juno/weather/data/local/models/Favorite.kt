package com.juno.weather.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name  = "id")
    var id: Long = 0,

    @ColumnInfo(name  = "city_id")
    var cityId: Int = 0,

    @ColumnInfo(name = "city_name")
    var cityName: String,

    @ColumnInfo(name = "country")
    var country: String
)