package com.prathap.weather.models

/***
 *  wrapper for Forecast data and error handling
 */
data class CitiesWeatherResults(
        val success: Boolean,
        val error: String = "",
        val data: CitiesWeather? = null
)