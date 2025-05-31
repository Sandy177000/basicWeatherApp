package com.example.weatherapp.data.model

data class ForecastResponse(
    val list: List<Forecast>,
    val city: City
)

data class City(
    val name: String,
    val population: Double
)