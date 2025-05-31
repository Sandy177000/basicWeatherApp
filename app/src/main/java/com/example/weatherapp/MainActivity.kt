package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.ui.main.ForecastAdapter
import com.example.weatherapp.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity is created")
        setContentView(R.layout.activity_main)
        weatherOnCreate()
    }

    private fun weatherOnCreate() {
        val apiKey = "819c9a5a5e070642cd928f9f68e8f38d"

        // UI elements
        val etCityName = findViewById<EditText>(R.id.etCityName)
        val btnGetWeather = findViewById<Button>(R.id.btnGetWeather)
        val tvTemperature = findViewById<TextView>(R.id.tvTemperature)
        val tvCondition = findViewById<TextView>(R.id.tvCondition)
        val error = findViewById<TextView>(R.id.error)
        val rvForecast = findViewById<RecyclerView>(R.id.rvForecast)

        viewModel.weatherData.observe(this, { response ->
            tvTemperature.text = "Temperature: ${response.main.temp} degC"
            tvCondition.text = "Condition: ${response.weather?.firstOrNull()?.description}"
            error.visibility =  View.GONE
        })

        viewModel.error.observe(this, { response ->
            tvTemperature.text = "Temperature: "
            tvCondition.text = "Condition: "
            rvForecast.adapter = ForecastAdapter(emptyList())
            error.text = response.toString()
            error.visibility = View.VISIBLE
        })


        // 1. initialise with empty data
        forecastAdapter = ForecastAdapter(emptyList())
        // 2. set the layout - orientation, reversing, with context
        rvForecast.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        // 3. attach the adapter
        rvForecast.adapter = forecastAdapter

        viewModel.forecastData.observe(this, { forecastData ->
            rvForecast.adapter = ForecastAdapter(forecastData.list)
        })

        btnGetWeather.setOnClickListener {
            val city = etCityName.text.toString().trim()
            if (city.isNotEmpty()){
                viewModel.fetchWeatherByCity(city, apiKey)
                viewModel.fetchForecastByCity(city, apiKey)
            } else {
                tvTemperature.text = "Temperature: "
                tvCondition.text = "Condition: "
                error.text = "Please enter a city name"
            }
        }
    }




}