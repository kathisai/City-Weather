package com.prathap.weather.di.modules

import android.app.Application
import androidx.room.Room
import com.prathap.weather.database.CityWeatherDatabase
import com.prathap.weather.database.dao.CityWeatherDao
import com.prathap.weather.network.ConnectivityInterceptor
import com.prathap.weather.network.RequestInterceptor
import com.prathap.weather.network.WeatherApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * * for all Generic App Injection
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun getConnectivityInterceptor(app: Application): ConnectivityInterceptor {
        return ConnectivityInterceptor(app.applicationContext)
    }

    @Singleton
    @Provides
    fun getRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Singleton
    @Provides
    fun getRetrofitService(
        connectivityInterceptor: ConnectivityInterceptor,
        requestInterceptor: RequestInterceptor
    ): WeatherApiService {
        return WeatherApiService.invoke(
            connectivityInterceptor = connectivityInterceptor,
            requestInterceptor = requestInterceptor
        )
    }

    @Singleton
    @Provides
    fun getCityWeatherDatabase(app: Application): CityWeatherDatabase {
        return Room
            .databaseBuilder(app, CityWeatherDatabase::class.java, "bookmarks.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun getCityWeatherDao(db: CityWeatherDatabase): CityWeatherDao {
        return db.cityDao()
    }


}