package com.example.weatherapp.repository

import com.example.weatherapp.model.currentweather.WeatherResult
import com.example.weatherapp.model.forecast.Coord
import com.example.weatherapp.model.forecast.FiveDayForecast

/**
 * Provides Api connection with http://openweathermap.org/
 */
interface WeatherRepository {
    /**
     *Getting location info like lat and lon
     */
    suspend fun getLocationCoordinates(city : String) : Coord

    /**
     * Getting current weather for special place by provide lat and lon
     */
    suspend fun getCurrentWeather(lat : Double, lon : Double) : WeatherResult

    /**
     * Getting forecast for specific place by provide lat and lon
     */
    suspend fun getForecast(lat : Double, lon : Double) : FiveDayForecast
}