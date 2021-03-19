package com.juno.weather.data.remote.models.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeather(
    @SerializedName("id") val id: Int?,
    @SerializedName("main") val name: String?,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
) : Parcelable