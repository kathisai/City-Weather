package com.prathap.weather.database.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Represents one record of the CityWeather table.
 */
@Entity(tableName = CityWeather.TABLE_NAME)
class CityWeather {
    /** The unique ID of the City.  */
    @PrimaryKey()
    @ColumnInfo(index = true, name = COLUMN_ID)
    var id: Long = 0


    /** Latitude  */
    @ColumnInfo(name = COLUMN_LAT)
    var latitude: String? = null


    /** longitude  */
    @ColumnInfo(name = COLUMN_LAN)
    var longitude: String? = null


    companion object {
        /** The name of the table in bookmarks.db.  */
        const val TABLE_NAME = "cityweather"

        /** The name of the ID column.  */
        const val COLUMN_ID = "cityID"


        const val COLUMN_LAT = "Lat"

        const val COLUMN_LAN = "Lan"
    }
}