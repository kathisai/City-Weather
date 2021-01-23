package com.prathap.weather.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prathap.weather.database.entities.CityWeather


/**
 * Data access object for Cheese.
 */
@Dao
interface CityWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityWeather: CityWeather?): Long

    @Query("SELECT * FROM " + CityWeather.TABLE_NAME + " WHERE " + CityWeather.COLUMN_ID + " = :id")
    fun selectById(id: Long): Cursor?

    @Query("DELETE FROM " + CityWeather.TABLE_NAME + " WHERE " + CityWeather.COLUMN_ID + " = :id")
    fun deleteById(id: Long): Int


    @Query("SELECT * FROM " + CityWeather.TABLE_NAME)
    fun getBookMarkedCities(): LiveData<List<CityWeather>>

}