package com.juno.weather.ui.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.juno.weather.R
import com.juno.weather.data.remote.OpenWeatherRequests
import com.juno.weather.data.remote.models.City
import com.juno.weather.data.remote.models.FindResult
import com.juno.weather.databinding.FragmentSearchBinding
import com.juno.weather.utils.MarginItemDecoration
import com.juno.weather.utils.isInternetAvailable
import com.juno.weather.utils.showMessage
import com.juno.weather.utils.toPX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    companion object {
        private const val TAG = "SearchFragment"
    }

    private lateinit var binding: FragmentSearchBinding
    private val adapter: SearchAdapter by lazy { SearchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.recyclerViewResults.adapter = adapter
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewResults.addItemDecoration(MarginItemDecoration(16.toPX()))

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
        val call = OpenWeatherRequests.getOpenWeatherService().findCity(
            cityName,
            "metric",
            "PT",
            "0fd029d20a3a93a1ec448df0ba8edbd8"
        )

        call.enqueue(object : Callback<FindResult<City>> {
            override fun onResponse(call: Call<FindResult<City>>, response: Response<FindResult<City>>) {
                if (response.isSuccessful) {
                    adapter.submitList(response.body()?.list)
                } else {
                    Log.w(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FindResult<City>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }
}