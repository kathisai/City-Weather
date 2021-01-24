package com.prathap.weather.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.prathap.weather.database.dao.CityWeatherDao
import com.prathap.weather.database.entities.CityWeather
import com.prathap.weather.utils.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CityRepository to do CURD operation on DB
 */
@Singleton
@OpenForTesting
class CityRepository @Inject constructor(private val cityDao: CityWeatherDao) {

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