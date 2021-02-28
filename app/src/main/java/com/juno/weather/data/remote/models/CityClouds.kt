package com.juno.weather.data.remote.models

import com.google.gson.annotations.SerializedName

data class CityClouds(
    @SerializedName("all") val all: Int
)