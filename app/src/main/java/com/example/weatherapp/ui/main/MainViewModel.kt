package com.example.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.ForecastResponse
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import retrofit2.Call


import retrofit2.Callback
import retrofit2.Response


 /**Inheritance :
 ViewModel is the base class used to build a component which is similar to
 Functional Component with hooks / Context / Redux in React

 Why use ViewModel?
 Keeps UI code clean (separation of concerns)
 Survives lifecycle changes (like screen rotation)
 Makes testing easier (logic is separate from UI)

 **/

class MainViewModel: ViewModel() {

    private val repository = WeatherRepository()

    /** Encapsulation :
     * By making _weatherData private, only the MainViewModel can change the data.
     * By exposing only the LiveData version (weatherData),
     * we ensure that the Activity or Fragment can observe, but cannot directly change the data
     */

     /** LiveData is similar to a state in React, LiveData has only read **/

     private val _weatherData = MutableLiveData<WeatherResponse>()
     val weatherData : LiveData<WeatherResponse> = _weatherData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

     private val _forecastData = MutableLiveData<ForecastResponse>()
     val forecastData: LiveData<ForecastResponse> = _forecastData

     /**
     enqueue makes the actual network call
     we have to implement what needs to be done on the callbacks {onResponse, onFailure} it provides
      **/
    fun fetchWeatherByCity(city: String, apiKey: String) {
        repository.getWeatherByCity(city, apiKey)
            .enqueue(object: Callback<WeatherResponse> {

                /** Polymorphism :
                 Runtime Polymorphism (method overriding)**/

                override fun onResponse(
                    call: Call<WeatherResponse>, // contains request info, mostly not required
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful && response.body()!==null) {
                       _weatherData.postValue(response.body()) // similar to setState() _weatherData
                    } else {
                        _error.postValue("Error ${response.message()}") // similar to setState() error
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    println(t.message)
                    _error.postValue("Failure ${t.message}")
                }
            })
    }


     fun fetchForecastByCity(city: String, apiKey: String) {
         repository.getForecastByCity(city, apiKey)
             .enqueue(object: Callback<ForecastResponse> {
                 override fun onResponse(
                     call: Call<ForecastResponse>,
                     response: Response<ForecastResponse>
                 ) {
                     if(response.isSuccessful && response.body() != null) {
                         _forecastData.postValue(response.body())
                     } else {
                         _error.postValue("Error while fetching forecast ${response.message()}")
                     }
                 }

                 override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                     _error.postValue("Failure ${t.message}")
                 }
             })
     }
}