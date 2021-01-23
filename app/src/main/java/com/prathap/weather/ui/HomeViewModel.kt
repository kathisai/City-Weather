package com.prathap.weather.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prathap.weather.database.entities.CityWeather
import com.prathap.weather.models.CitiesWeatherResults
import com.prathap.weather.models.WeatherDataResults
import com.prathap.weather.models.WeatherForecast
import com.prathap.weather.repository.CityRepository
import com.prathap.weather.repository.OpenWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val repository: OpenWeatherRepository,
        private val cityRepository: CityRepository
) : ViewModel() {
    val cityWeather = ArrayList<WeatherForecast>()

    private val _dataResults = MutableLiveData<CitiesWeatherResults>()
    val dataResults: LiveData<CitiesWeatherResults>
        get() = _dataResults

    val bookmarks: LiveData<List<CityWeather>>
        get() = cityRepository.getBookmarks()


    private val _cityResults = MutableLiveData<WeatherDataResults>()
    val cityResults: LiveData<WeatherDataResults>
        get() = _cityResults

    fun getBookMarkedData(city_ids: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val weatherDataResults = repository.getGroupWeatherData(city_ids)
            _dataResults.postValue(weatherDataResults)
        }
    }

    fun addBookmarkToDB(cityID: Long, lat: String, lon: String) {
        GlobalScope.launch(Dispatchers.IO) {
            cityRepository.insert(CityWeather().apply {
                id = cityID
                latitude = lat
                longitude = lon
            })
        }
    }

    fun getCityWeather(latitude: Double, longitude: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            val weatherDataResults = repository.getCityWeatherDataByLatLon(lat = latitude.toString(), lon = longitude.toString())
            _cityResults.postValue(weatherDataResults)
        }
    }


}