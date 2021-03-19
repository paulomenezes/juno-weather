package com.juno.weather.data.remote.models.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityCountry(
    @SerializedName("country") val name: String,
) : Parcelable