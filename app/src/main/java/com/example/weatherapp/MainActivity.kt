package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch{
            val result = retrofitClient.getGeocoding("London",limit = "1","45d5bc00fe8f4f8564a6a54bd8a1b85c")
            Log.d("testlog","--------->${result.body()}")
        }
    }
}