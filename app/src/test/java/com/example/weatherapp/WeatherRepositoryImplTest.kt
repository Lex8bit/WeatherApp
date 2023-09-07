package com.example.weatherapp

import com.example.weatherapp.model.currentweather.Clouds
import com.example.weatherapp.model.currentweather.Coord
import com.example.weatherapp.model.currentweather.CurrentWeather
import com.example.weatherapp.model.currentweather.Main
import com.example.weatherapp.model.currentweather.Sys
import com.example.weatherapp.model.currentweather.Weather
import com.example.weatherapp.model.currentweather.Wind
import com.example.weatherapp.model.forecast.City
import com.example.weatherapp.model.forecast.FiveDayForecast
import com.example.weatherapp.model.forecast.Forecast
import com.example.weatherapp.model.geocoding.Geocoding
import com.example.weatherapp.model.geocoding.GeocodingItem
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.repository.WeatherRepositoryImpl
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.APP_ID
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.LIMIT
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.METRIC
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import com.example.weatherapp.model.forecast.Clouds as fClouds
import com.example.weatherapp.model.forecast.Coord as fCoord
import com.example.weatherapp.model.forecast.Main as fMain
import com.example.weatherapp.model.forecast.Rain as fRain
import com.example.weatherapp.model.forecast.Sys as fSys
import com.example.weatherapp.model.forecast.Weather as fWeather
import com.example.weatherapp.model.forecast.Wind as fWind

class WeatherRepositoryImplTest {

    private val lat = 37.7749
    private val lon = -122.4194

    private val weatherApiMock: WeatherApi = mock()
    private lateinit var subject: WeatherRepositoryImpl

    @Before
    fun setUp(){
        subject = WeatherRepositoryImpl(weatherApiMock)
    }

    @Test
    fun `getLocationCoordinates success response` ():Unit = runBlocking {
        //Setup
        val city = "London"
        val expectedLat = 37.7749
        val expectedLon = -122.4194
        val location = Geocoding()
        location.add(GeocodingItem("USA",37.7749, null,-122.4194,"San Francisco","SF"))
        location.add(GeocodingItem("USA",40.7128,null,-74.0060,"New York","NY"))
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getGeocoding(city,LIMIT, APP_ID)).thenReturn(mockResponse)

        //Call
        val result = subject.getLocationCoordinates(city)

        //Verification //use 0.0 bc Deprecated function
        assertEquals(expectedLat, result.lat,0.0)
        assertEquals(expectedLon, result.lon,0.0)
    }

    @Test
    fun `getCurrentWeather success response` ():Unit = runBlocking {
        //Setup
        val currentWeatherResult = CurrentWeather(
            "base",
            Clouds(1),1, Coord(lat,lon),1,1,
            Main(1.0,1,10,10,1,10.0,1.0,1.0),
            "name",
            Sys("country",1,1,1,1),2,1,
            listOf(Weather("description","icon",1,"main")),
            Wind(1,1.0,10.0)
        )
        val mockResponse = Response.success(currentWeatherResult)
        `when`(weatherApiMock.getCurrentWeather(lat,lon,APP_ID,METRIC)).thenReturn(mockResponse)

        //Call
        val result = subject.getCurrentWeather(lat,lon)

        //Verification //use 0.0 bc Deprecated function
        assertEquals("main",result.main)
        assertEquals("description",result.description)
    }

    @Test
    fun getForecast_success ():Unit = runBlocking {
        //Setup
        val expectedFiveDayForecast = FiveDayForecast(
            City(fCoord(lat, lon), "Country", 1, "name", 1, 1, 1, 1),
            1,
            "cod",
            listOf(
                Forecast(
                    fClouds(1), 1, "dt_txt",
                    fMain(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0,1.0),
                    1.0, fRain(1.0), fSys("pod"), 1,
                    listOf(fWeather("description", "icon", 1, "main")),
                    fWind(1, 1.0, 10.0)
                )
            ),
            1
        )
        val mockResponse = Response.success(expectedFiveDayForecast)
        `when`(weatherApiMock.getForecast(lat,lon,APP_ID,METRIC)).thenReturn(mockResponse)

        //Call
        val actualForecast = subject.getForecast(lat,lon)

        //Verification //use 0.0 bc Deprecated function
        assertEquals(expectedFiveDayForecast,actualForecast)
    }
}