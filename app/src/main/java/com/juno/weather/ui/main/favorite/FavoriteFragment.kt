package com.juno.weather.ui.main.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juno.weather.data.local.WeatherDatabase
import com.juno.weather.data.local.models.Favorite
import com.juno.weather.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

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

        val dao = WeatherDatabase.getInstance(requireContext()).getFavorite()

        dao.insert(Favorite(333, "Opa"))

        val l = dao.getAll()

        Log.d("FavoriteFragment", l.toString())
        Log.d("FavoriteFragment", dao.getById(123L).toString())
    }
}