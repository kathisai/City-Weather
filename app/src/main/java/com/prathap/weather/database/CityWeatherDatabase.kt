package com.prathap.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prathap.weather.database.dao.CityWeatherDao
import com.prathap.weather.database.entities.CityWeather

/**
 * The Room database with version 1 update this class every time if we modify db schema
 */
@Database(entities = [CityWeather::class], version = 1, exportSchema = false)
abstract class CityWeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityWeatherDao

}