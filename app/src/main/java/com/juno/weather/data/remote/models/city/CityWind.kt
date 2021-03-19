package com.juno.weather.data.remote.models.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWind(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val degrees: Float?
) : Parcelable