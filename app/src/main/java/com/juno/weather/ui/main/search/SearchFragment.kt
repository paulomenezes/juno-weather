package com.juno.weather.ui.main.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.juno.weather.R
import com.juno.weather.data.local.WeatherDatabase
import com.juno.weather.data.local.models.WeatherCity
import com.juno.weather.data.local.models.WeatherRequest
import com.juno.weather.data.remote.OpenWeatherRequests
import com.juno.weather.data.remote.models.*
import com.juno.weather.data.remote.models.city.*
import com.juno.weather.databinding.FragmentSearchBinding
import com.juno.weather.ui.forecast.ForecastActivity
import com.juno.weather.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SearchFragment : Fragment() {
    companion object {
        private const val TAG = "SearchFragment"
        private const val SAVE_INPUT = "inputCity"

        const val PARAM_CITY = "city"
    }

    private lateinit var binding: FragmentSearchBinding
    private val preferences by lazy { Preferences(requireContext()) }
    private val adapter: SearchAdapter by lazy { SearchAdapter(requireContext()) { city ->
        val intent = Intent(requireContext(), ForecastActivity::class.java)
        intent.putExtra(PARAM_CITY, city)

        startActivity(intent)
    } }
    private val keyStorage by lazy { KeyStorage(requireContext()) }
    private val weatherRequestDAO by lazy { WeatherDatabase.getInstance(requireContext()).weatherRequestDAO() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            binding.editTextSearch.setText(savedInstanceState.getString(SAVE_INPUT, ""))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(SAVE_INPUT, binding.editTextSearch.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.recyclerViewResults.adapter = adapter
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewResults.addItemDecoration(MarginItemDecoration(16.toPX()))

        binding.editTextSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findCity()
                return@OnEditorActionListener true
            }
            false
        })

        binding.buttonSearch.setOnClickListener {
            findCity()
        }
    }

    private fun findCity() {
        if (requireContext().isInternetAvailable()) {
            if (binding.editTextSearch.text.isNotEmpty()) {
                requestCity(binding.editTextSearch.text.toString())
            } else {
                showMessage(binding.root, R.string.empty_search)
            }
        } else {
            showMessage(binding.root, R.string.no_internet)
        }
    }

    private fun requestCity(cityName: String) {
        val unit = preferences.getUnit()
        val language = preferences.getLanguage()
        val offline = preferences.getOffline()

        if (unit != null && language != null) {
            if (offline) {
                requestFromLocal(cityName, unit, language)
            } else {
                val apiKey = keyStorage.read()

                if (apiKey != null) {
                    showLoading()
                    requestFromAPI(cityName, unit, language, apiKey)
                }
            }
        }
    }

    private fun requestFromAPI(cityName: String, unit: String, language: String, apiKey: String) {
        val call = OpenWeatherRequests.getOpenWeatherService().findCity(
            cityName,
            unit,
            language,
            apiKey
        )

        call.enqueue(object : Callback<FindResult<City>> {
            override fun onResponse(
                call: Call<FindResult<City>>,
                response: Response<FindResult<City>>
            ) {
                val body = response.body()

                if (response.isSuccessful && body != null && body.list.isNotEmpty()) {
                    val id = weatherRequestDAO.insert(
                        WeatherRequest(
                            0,
                            Date(),
                            cityName.toLowerCase(),
                            unit,
                            language
                        )
                    )

                    for (item in body.list) {
                        weatherRequestDAO.insertCity(
                            WeatherCity(
                                0,
                                id,
                                item.weather[0].icon,
                                item.id,
                                item.name,
                                item.country.name,
                                item.weather[0].description,
                                item.main.temp,
                                item.clouds.all,
                                item.wind.speed
                            )
                        )
                    }

                    adapter.submitList(response.body()?.list)
                } else {
                    Log.w(TAG, "onResponse: ${response.message()}")
                }

                hideLoading()
            }

            override fun onFailure(call: Call<FindResult<City>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)

                hideLoading()
            }
        })
    }

    private fun requestFromLocal(cityName: String, unit: String, language: String) {
        val weather = weatherRequestDAO.getWeatherRequest(cityName.toLowerCase(), unit, language)

        val cities = mutableListOf<City>()

        if (weather != null) {
            for (city in weather.cities) {
                cities.add(
                    City(
                        city.cityId,
                        city.name,
                        null,
                        null,
                        CityWeatherInfo(
                            city.temperature,
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                        CityCountry(city.country),
                        CityClouds(city.clouds),
                        CityWind(city.windSpeed, null),
                        listOf(
                            CityWeather(
                                null,
                                null,
                                city.description,
                                city.icon
                            )
                        )
                    )
                )
            }
        }

        adapter.submitList(cities)
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.loadingBackdrop.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.INVISIBLE
        binding.loadingBackdrop.visibility = View.INVISIBLE
    }
}