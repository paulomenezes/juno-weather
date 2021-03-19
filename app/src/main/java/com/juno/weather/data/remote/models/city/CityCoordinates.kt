package com.juno.weather.data.remote.models.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityCoordinates(
    @SerializedName("lat") val latitude: Float,
    @SerializedName("lon") val longitude: Float
) : Parcelable