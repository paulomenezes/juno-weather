package com.juno.weather.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.juno.weather.R
import com.juno.weather.data.remote.models.City
import com.juno.weather.databinding.ItemSearchCityBinding

class SearchAdapter : ListAdapter<City, SearchAdapter.ViewHolder>(SearchDiff()) {
    inner class ViewHolder(private val binding: ItemSearchCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            val iconURL = "http://openweathermap.org/img/wn/${city.weather[0].icon}@4x.png"

            binding.textViewCityName.text = city.name
            binding.textViewCountry.text = city.country.name
            binding.imageViewWeather.load(iconURL) {
                crossfade(true)
                placeholder(R.drawable.ic_weather_placeholder)
            }
        }
    }

    class SearchDiff : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}