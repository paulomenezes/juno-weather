package com.juno.weather.ui.forecast

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.juno.weather.R
import com.juno.weather.data.remote.models.forecast.Forecast
import com.juno.weather.databinding.ItemForecastBinding
import com.juno.weather.utils.Preferences
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ForecastAdapter(private val context: Context) : ListAdapter<Forecast, ForecastAdapter.ViewHolder>(SearchDiff()) {
    private val preferences = Preferences(context)

    inner class ViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast) {
            val iconURL = "http://openweathermap.org/img/wn/${forecast.weather[0].icon}@4x.png"

            val date = Date(forecast.date * 1000)

            val formatter = SimpleDateFormat(context.getString(R.string.forecast_date_format), Locale.US)

            binding.textViewForecastDate.text = formatter.format(date)
            binding.textViewForecastDescription.text = forecast.weather[0].description

            binding.textViewForecastItemTemperature.text = forecast.main.temp.toInt().toString()
            binding.textViewForecastItemUnit.text = preferences.getUnitLabel()

            binding.textViewForecastItemClouds.text = context.getString(R.string.clouds_percent, forecast.clouds.all.toString())
            binding.textViewForecastItemSpeed.text = forecast.wind.speed.toString()

            binding.imageViewForecastItemWeather.load(iconURL) {
                crossfade(true)
                placeholder(R.drawable.ic_weather_placeholder)
            }
        }
    }

    class SearchDiff : DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}