package com.juno.weather.ui.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.juno.weather.data.remote.models.city.City
import com.juno.weather.databinding.ActivityForecastBinding
import com.juno.weather.ui.main.search.SearchFragment
import com.juno.weather.R
import com.juno.weather.data.local.WeatherDatabase
import com.juno.weather.data.local.models.Favorite
import com.juno.weather.data.local.models.WeatherCity
import com.juno.weather.data.local.models.WeatherRequest
import com.juno.weather.data.remote.OpenWeatherRequests
import com.juno.weather.data.remote.models.FindResult
import com.juno.weather.data.remote.models.forecast.Forecast
import com.juno.weather.utils.KeyStorage
import com.juno.weather.utils.MarginItemDecoration
import com.juno.weather.utils.Preferences
import com.juno.weather.utils.toPX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForecastBinding

    private val keyStorage by lazy { KeyStorage(this) }
    private val favoriteDAO by lazy { WeatherDatabase.getInstance(this).getFavorite() }
    private val preferences by lazy { Preferences(this) }
    private val forecastAdapter by lazy { ForecastAdapter(this) }

    private var isFavorite = false
    private var favoriteId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForecastBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val city = intent.getParcelableExtra<City>(SearchFragment.PARAM_CITY)

        if (city != null) {
            initUI(city)
            requestForecast(city)
        }
    }

    private fun initUI(city: City) {
        binding.textViewTitle.text = getString(R.string.forecast_title, city.name, city.country.name)
        binding.textViewForecastTemperature.text = city.main.temp.toInt().toString()
        binding.textViewForecastUnit.text = preferences.getUnitLabel()

        val iconURL = "http://openweathermap.org/img/wn/${city.weather[0].icon}@4x.png"

        binding.imageViewForecastWeather.load(iconURL) {
            crossfade(true)
            placeholder(R.drawable.ic_weather_placeholder)
        }

        val favorite = favoriteDAO.getById(city.id)
        isFavorite = favorite != null
        favoriteId = favorite?.id

        setFavoriteIcon()

        if (favorite != null) {
            binding.buttonFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_border_24_white, 0, 0, 0)
        } else {
            binding.buttonFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_24_white, 0, 0, 0)
        }

        binding.buttonFavorite.setOnClickListener {
            if (isFavorite && favoriteId != null) {
                favoriteDAO.delete(favoriteId!!)
            } else {
                favoriteId = favoriteDAO.insert(Favorite(0, city.id, city.name, city.country.name))
            }

            isFavorite = !isFavorite

            setFavoriteIcon()
        }

        binding.recyclerViewForecast.adapter = forecastAdapter
        binding.recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForecast.addItemDecoration(MarginItemDecoration(16.toPX()))
    }

    private fun setFavoriteIcon() {
        if (isFavorite) {
            binding.buttonFavorite.text = getString(R.string.unfavorite_action)
            binding.buttonFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_border_24_white, 0, 0, 0)
        } else {
            binding.buttonFavorite.text = getString(R.string.favorite_action)
            binding.buttonFavorite.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_favorite_24_white, 0, 0, 0)
        }
    }

    private fun requestForecast(city: City) {
        val unit = preferences.getUnit()
        val language = preferences.getLanguage()
        val apiKey = keyStorage.read()

        if (unit != null && language != null && apiKey != null ) {
            val call = OpenWeatherRequests.getOpenWeatherService().getForecast(
                city.id,
                unit,
                language,
                apiKey
            )

            call.enqueue(object : Callback<FindResult<Forecast>> {
                override fun onResponse(
                    call: Call<FindResult<Forecast>>,
                    response: Response<FindResult<Forecast>>
                ) {
                    val body = response.body()

                    if (response.isSuccessful && body != null && body.list.isNotEmpty()) {
                        forecastAdapter.submitList(body.list)
                    }
                }

                override fun onFailure(call: Call<FindResult<Forecast>>, t: Throwable) {

                }
            })
        }
    }
}