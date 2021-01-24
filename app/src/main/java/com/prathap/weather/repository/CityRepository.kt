package com.prathap.weather.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.prathap.weather.database.CityWeatherDatabase
import com.prathap.weather.database.dao.CityWeatherDao
import com.prathap.weather.database.entities.CityWeather
import javax.inject.Inject
import javax.inject.Singleton

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#11

@Singleton
class CityRepository @Inject constructor(private val db: CityWeatherDatabase,
                                         private val cityDao: CityWeatherDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: CityWeather) {
        cityDao.insert(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Long) {
        cityDao.deleteById(id)
    }


    fun getBookmarks(): LiveData<List<CityWeather>> {
        return cityDao.getBookMarkedCities()
    }
}