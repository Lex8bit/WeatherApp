package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.model.currentweather.WeatherResult
import com.example.weatherapp.model.forecast.City
import com.example.weatherapp.model.forecast.Coord
import com.example.weatherapp.model.forecast.FiveDayForecast
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    /**
     * Because LifeData we need add this rule
     */
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()


    private val weatherRepositoryMock:WeatherRepository = mock()
    private lateinit var viewModel : MainViewModel

    @Before
    fun setUp(){
        viewModel = MainViewModel(weatherRepositoryMock)
    }

    /**
     * runBlocking - для тестирования suspend fun
     */
    @Test
    fun `test gerCoordinates`() = runBlocking {
        //Setup
        val city = "TestCity"
        val coordinates = Coord(1.0,1.0)
        `when` (weatherRepositoryMock.getLocationCoordinates(city)).thenReturn(coordinates)
        //Call
        viewModel.getCoordinates(city)
        //Verification
        assertEquals(coordinates,viewModel.coordinatesResult.value)
    }

    @Test
    fun `test getCurrentWeather`() = runBlocking {
        //Setup
        val lat =0.0
        val lon =0.0
        val weatherResult = WeatherResult("main","description",1.0,1,1,1.1)
        `when` (weatherRepositoryMock.getCurrentWeather(lat,lon)).thenReturn(weatherResult)
        //Call
        viewModel.getCurrencyWeather(lat,lon)
        //Verification
        assertEquals(weatherResult,viewModel.currentWeatherResult.value)
    }
    @Test
    fun `test getForecast`() = runBlocking {
        //Setup
        val lat =0.0
        val lon =0.0
        val forecast = FiveDayForecast(
            City(Coord(1.0,1.0),"Country",1,"name",100,200,201,31),
            1,"cod", emptyList(),1)
        `when` (weatherRepositoryMock.getForecast(lat,lon)).thenReturn(forecast)
        //Call
        viewModel.getForecast(lat,lon)
        //Verification
        assertEquals(forecast,viewModel.forecastResult.value)
    }



}