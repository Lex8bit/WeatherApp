package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout

    private val addId = "45d5bc00fe8f4f8564a6a54bd8a1b85c"
    //type safety,null safety,improved performance,and better readability
    private lateinit var binding : ActivityMainBinding

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ИНИЦИАЛИЗИРУЕМ БИНДИНГ
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

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
//                locationLable.text = "Location: $latResult, $lonResult"
//                currentWeatherLable.text = currentWeather.body()?.weather?.first()?.description ?: ""
//                forecastLable.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""
            }
        }

        prepareViewPager()

    }

    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            WeatherFragment.newInstance(),
            ForecastFragment.newInstance()
        )
        val tabTitlesArray = arrayOf("Weather","Forecast")

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitlesArray[position]
        }.attach()
    }
}