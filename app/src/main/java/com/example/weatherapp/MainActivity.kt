package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var locationLable : TextView
    private lateinit var currentWeatherLable : TextView
    private lateinit var forecastLable : TextView

    private val addId = "45d5bc00fe8f4f8564a6a54bd8a1b85c"


    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationLable = findViewById(R.id.locationLable)
        currentWeatherLable = findViewById(R.id.currentWeatherLable)
        forecastLable = findViewById(R.id.forecastLable)

        lifecycleScope.launch(Dispatchers.IO){
            val result = retrofitClient.getGeocoding("London",limit = "1",addId)
            val latResult = result.body()?.first()?.lat ?: 0.0
            val lonResult = result.body()?.first()?.lon ?: 0.0

            val currentWeather = retrofitClient.getCurrentWeather(latResult,lonResult,addId,"metric")
            val forecast = retrofitClient.getForecast(latResult,lonResult,addId,"metric")

            Log.d("testlog","Geocoding-------> ${result.body()}")
            Log.d("testlog","CurrentWeather-------> ${currentWeather.body()}")
            Log.d("testlog","Forecast-------> ${forecast.message()}")
            Log.d("testlog","Forecast-------> ${forecast.isSuccessful}")
            Log.d("testlog","Forecast-------> ${forecast.body()}")

            withContext(Dispatchers.Main){
                locationLable.text = "Location: $latResult, $lonResult"
                currentWeatherLable.text = currentWeather.body()?.weather?.first()?.description ?: ""
                forecastLable.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""
            }
        }
    }
}