package com.prathap.weather.testutils

import com.prathap.weather.models.*

object TestUtil {

    fun createDummyWeatherForecast(): WeatherForecast {
        return WeatherForecast(
            Coord(0.0, 0.0),
            listOf(
                Weather(802, "Clouds", "scattered clouds", "03d")
            ),
            "stations",
            Main(
                300.74,
                303.33,
                300.74,
                300.74,
                1009,
                72
            ),
            10000,
            Wind(
                3.07,
                222
            ),
            Clouds(46),
            1611511187,
            Sys(0, 0, "Globe", 1611468507, 1611512129),
            0,
            6295630,
            "Globe",
            200

        )
    }

}
