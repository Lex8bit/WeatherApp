package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.currentweather.WeatherResult
import com.example.weatherapp.model.forecast.Coord
import com.example.weatherapp.model.forecast.FiveDayForecast
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) :ViewModel() {

    private val coordinates: MutableLiveData<Coord> = MutableLiveData()
    val coordinatesResult : LiveData<Coord> = coordinates

    private val currentWeather: MutableLiveData<WeatherResult> = MutableLiveData()
    val currentWeatherResult : LiveData<WeatherResult> = currentWeather

    private val forecast: MutableLiveData<FiveDayForecast> = MutableLiveData()
    val forecastResult : LiveData<FiveDayForecast> = forecast

    suspend fun getCoordinates(city : String){
        withContext(Dispatchers.IO){
            val coordinatesApiResult = weatherRepository.getLocationCoordinates(city)
            coordinates.postValue(coordinatesApiResult)
        }
    }

    suspend fun getCurrencyWeather(lat: Double, lon: Double){
        withContext(Dispatchers.IO){
            val currentWeatherResult = weatherRepository.getCurrentWeather(lat,lon)
            currentWeather.postValue(currentWeatherResult)
        }
    }

    suspend fun  getForecast(lat: Double, lon: Double){
        withContext(Dispatchers.IO){
            val currentForecast = weatherRepository.getForecast(lat,lon)
            forecast.postValue(currentForecast)
        }
    }

}