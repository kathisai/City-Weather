package com.prathap.weather.models

import com.google.gson.annotations.SerializedName

data class CitiesWeather(

        @SerializedName("cnt") val cnt: Int,
        @SerializedName("list") val list: List<WeatherForecast>
)