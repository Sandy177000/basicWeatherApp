package com.example.weatherapp.repository

import com.example.weatherapp.data.model.ForecastResponse
import com.example.weatherapp.data.network.RetrofitInstance
import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.Call

class WeatherRepository {
    fun getWeatherByCity(city: String, apiKey: String): Call<WeatherResponse> {
        return RetrofitInstance.api.getWeatherByCity(city, apiKey)
    }

    fun getForecastByCity(city: String, apiKey: String): Call<ForecastResponse> {
        return RetrofitInstance.api.getForecastByCity(city, apiKey)
    }
}
