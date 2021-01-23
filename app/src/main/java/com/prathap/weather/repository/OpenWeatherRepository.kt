package com.prathap.weather.repository


import com.prathap.weather.models.CitiesWeatherResults
import com.prathap.weather.models.WeatherDataResults
import com.prathap.weather.network.WeatherApiService
import com.prathap.weather.network.exceptions.BadRequestException
import com.prathap.weather.network.exceptions.InternalServerException
import com.prathap.weather.network.exceptions.NoConnectivityException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenWeatherRepository @Inject constructor(
        private val weatherApiService: WeatherApiService
) {
    suspend fun getWeatherData(): WeatherDataResults {
        try {
            val weatherData = weatherApiService.getForecastAsync(
                    city = "Hyderabad"
            ).await()

            return WeatherDataResults(
                    success = true,
                    data = weatherData
            )
        } catch (e: Exception) {
            return WeatherDataResults(
                    success = false,
                    error = "IllegalStateException $e"
            )
        } catch (e: NoConnectivityException) {
            return WeatherDataResults(
                    success = false,
                    error = "NoConnectivityException $e"
            )
        } catch (e: BadRequestException) {
            return WeatherDataResults(
                    success = false,
                    error = "BadRequestException $e"

            )
        } catch (e: InternalServerException) {
            return WeatherDataResults(
                    success = false,
                    error = "InternalServerException $e"
            )
        } catch (e: IllegalStateException) {
            return WeatherDataResults(
                    success = false,
                    error = "IllegalStateException $e"
            )
        }
    }

    suspend fun getGroupWeatherData(city_ids: String): CitiesWeatherResults {
        try {
            val weatherData = weatherApiService.getGroupForecastAsync(
                    city_id = city_ids
            ).await()

            return CitiesWeatherResults(
                    success = true,
                    data = weatherData
            )
        } catch (e: Exception) {
            return CitiesWeatherResults(
                    success = false,
                    error = "IllegalStateException $e"
            )
        } catch (e: NoConnectivityException) {
            return CitiesWeatherResults(
                    success = false,
                    error = "NoConnectivityException $e"
            )
        } catch (e: BadRequestException) {
            return CitiesWeatherResults(
                    success = false,
                    error = "BadRequestException $e"

            )
        } catch (e: InternalServerException) {
            return CitiesWeatherResults(
                    success = false,
                    error = "InternalServerException $e"
            )
        } catch (e: IllegalStateException) {
            return CitiesWeatherResults(
                    success = false,
                    error = "IllegalStateException $e"
            )
        }
    }

    suspend fun getCityWeatherDataByLatLon(lat: String, lon: String): WeatherDataResults {
        try {
            val weatherData = weatherApiService.getForecastByLatLonAsync(
                    lat = lat,
                    lon = lon
            ).await()

            return WeatherDataResults(
                    success = true,
                    data = weatherData
            )
        } catch (e: Exception) {
            return WeatherDataResults(
                    success = false,
                    error = "IllegalStateException $e"
            )
        }

    }


}
