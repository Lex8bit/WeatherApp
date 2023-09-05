package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        lifecycleScope.launch(Dispatchers.Main){
            mainViewModel.getCoordinates("London")
        }

        mainViewModel.coordinatesResult.observe(this, Observer {
            lifecycleScope.launch(Dispatchers.Main) {
                mainViewModel.getCurrencyWeather(it.lat, it.lon)
                mainViewModel.getForecast(it.lat, it.lon)
            }
        })
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