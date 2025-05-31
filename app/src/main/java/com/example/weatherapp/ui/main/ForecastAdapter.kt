package com.example.weatherapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.model.Forecast

/**
 This adapter is like a bridge between ui and data
 **/
class ForecastAdapter(private val forecastList: List<Forecast>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

        // a nested class extending view holder
        // this holds references to the ui or view later to be used to bind data
    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val index = itemView.findViewById<TextView>(R.id.tvIndex)
        val time = itemView.findViewById<TextView>(R.id.tvTime)
        val temp = itemView.findViewById<TextView>(R.id.tvTemp)
        val condition = itemView.findViewById<TextView>(R.id.tvCondition)
    }

    // this can be referred as renderItem in react native
    // It is called each time when recycler view needs to render a new row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {

        // LayoutInflater is a class which converts the xml code into view objects
        // Here we are preparing a single item's view which will be rendered in the list

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    // this is called by recycler view which binds the actual data with the ui(holder)
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.index.text = "$position"
        holder.time.text = forecast.dtTxt
        holder.temp.text = "${forecast.main.temp} degC"
        holder.condition.text = forecast.weather[0].description
    }

    // returns the count of items for recycler view
    override fun getItemCount() = forecastList.size
}
