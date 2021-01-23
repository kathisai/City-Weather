package com.prathap.weather.models

import com.google.gson.annotations.SerializedName


data class Coord(

        @SerializedName("lon") val lon: Double,
        @SerializedName("lat") val lat: Double
)