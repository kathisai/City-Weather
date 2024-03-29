package com.prathap.weather.utils

import kotlin.math.roundToInt

/**
 * To convert F to C
 */
fun Double.toCelsius(): String {
    //Convert  Fahrenheit to Celsius
    val celsius = ((this - 32) * 5) / 9
    return "${celsius.roundToInt()}°"
}