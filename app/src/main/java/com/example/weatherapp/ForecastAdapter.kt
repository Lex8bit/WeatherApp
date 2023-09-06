package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.model.forecast.ForecastResult
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryImpl
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastAdapter(
    private val fragmentContext: Context,
    private val forecastList: List<ForecastResult>
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemForecastBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(forecast: ForecastResult){
            val inputDataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputDataFormat = SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())

            val date = inputDataFormat.parse(forecast.date)
            val outputDate = date?.let { outputDataFormat.format(it) }
            binding.itemRecyclerDate.text = "Date: $outputDate"
            binding.itemRecyclerTemp.text = "Temp: ${forecast.temp}"
            binding.itemRecyclerDescription.text = "Weather: ${forecast.descriptor}"
            when(forecast.main){
                WeatherRepositoryImpl.WEATHER_TYPE_CLEAR -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.clear_sky
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_CLOUDS -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.clouds
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_SNOW -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.snow
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_THUNDERSTORM -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.thunderstorm
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_RAIN -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.heavy_rain
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_CLEAR -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.clear_sky
                        )
                    )
                }
                else -> {
                    binding.itemRecyclerImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.mist
                        )
                    )
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return (ViewHolder(binding,fragmentContext))
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.bindItem(forecast)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}