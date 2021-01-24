package com.prathap.weather.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prathap.weather.models.CitiesWeather
import com.prathap.weather.models.WeatherForecast
import com.prathap.weather.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Service
 */
interface WeatherApiService {

    @GET("group?")
    fun getGroupForecastAsync(
        @Query("id") city_id: String
    ): Deferred<CitiesWeather>

    @GET("forecast?")
    fun getForecastAsync(
        @Query("id") cityId: String
    ): Deferred<WeatherForecast>

    @GET("weather?")
    fun getForecastByLatLonAsync(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Deferred<WeatherForecast>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            requestInterceptor: RequestInterceptor
        ): WeatherApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}