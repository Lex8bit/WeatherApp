package com.example.weatherapp.model.forecast

import java.io.FileDescriptor

data class ForecastResult(
    val main : String,
    val descriptor : String,
    val temp : String,
    val date : String
)
