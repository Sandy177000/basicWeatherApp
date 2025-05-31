package com.example.weatherapp.data.model

data class Forecast(
    val dtTxt: String,
    val main: Main,
    val weather: List<Weather>
)


