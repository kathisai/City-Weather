package com.prathap.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.prathap.weather.models.WeatherDataResults
import com.prathap.weather.repository.CityRepository
import com.prathap.weather.repository.OpenWeatherRepository
import com.prathap.weather.testutils.TestUtil
import com.prathap.weather.ui.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class HomeViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val apiRepository = Mockito.mock(OpenWeatherRepository::class.java)
    private val dbRepository = Mockito.mock(CityRepository::class.java)
    private lateinit var viewModel: HomeViewModel

    @Before
    fun init() {
        viewModel = HomeViewModel(apiRepository, dbRepository)
    }

    @Test
    fun testGetData() = runBlocking {
        //Mock WeatherDataResults
        val dataResults = WeatherDataResults(
            success = true,
            data = TestUtil.createDummyWeatherForecast()
        )
        //Return mocked dataResults when we call getCityWeatherDataByLatLon
        whenever(apiRepository.getCityWeatherDataByLatLon("0.0", "0.0")).thenReturn(dataResults)
        //Mock WeatherDataResults to Observe when we call getCityWeather()
        val observer = mock<Observer<WeatherDataResults>>()
        // attach observer to cityResults in ViewModel
        viewModel.cityResults.observeForever(observer)
        // Call getCityWeather in Separate Thread pool
        withContext(Dispatchers.Default) {
            viewModel.getCityWeather(0.0, 0.0)
            delay(100)
        }
        // Assert same results
        verify(observer).onChanged(dataResults)
    }

    /**
     * Negative Test with success = false,
     */
    @Test
    fun testErrorResponse() = runBlocking {
        val dataResults = WeatherDataResults(success = false, data = null)
        whenever(apiRepository.getCityWeatherDataByLatLon("0.0", "0.0")).thenReturn(dataResults)

        val observer = mock<Observer<WeatherDataResults>>()
        viewModel.cityResults.observeForever(observer)

        withContext(Dispatchers.Default) {
            viewModel.getCityWeather(0.0, 0.0)
            delay(100)
        }

        verify(observer).onChanged(dataResults)
    }
}