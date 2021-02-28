package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class FindResult<T>(
    @SerializedName("message") val message: String,
    @SerializedName("cod") val code: String,
    @SerializedName("count") val count: Int,
    @SerializedName("list") val list: List<T>
)