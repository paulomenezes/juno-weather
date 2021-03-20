package com.juno.weather.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.juno.weather.data.local.WeatherDatabase
import com.juno.weather.data.local.models.Favorite
import com.juno.weather.databinding.FragmentFavoriteBinding
import com.juno.weather.utils.MarginItemDecoration
import com.juno.weather.utils.toPX

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteAdapter by lazy { FavoriteAdapter() {
        delete(it)
    } }
    private val favoriteDAO by lazy { WeatherDatabase.getInstance(requireContext()).getFavorite() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUI()
        loadFavorites(null)
    }

    private fun initUI() {
        binding.recyclerViewFavorites.adapter = favoriteAdapter
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.addItemDecoration(MarginItemDecoration(16.toPX()))

        binding.buttonFavoriteSearch.setOnClickListener {
            val text = binding.editTextFavoriteSearch.text

            if (text.isNotEmpty()) {
                loadFavorites(text.toString())
            } else {
                loadFavorites(null)
            }
        }
    }

    private fun loadFavorites(text: String?) {
        val favorites = if (text != null) {
            favoriteDAO.getByNameCountry("%$text%")
        } else {
            favoriteDAO.getAll()
        }

        favoriteAdapter.submitList(favorites)
    }

    private fun delete(id: Long) {
        favoriteDAO.delete(id)

        val text = binding.editTextFavoriteSearch.text

        loadFavorites(text.toString())
    }
}