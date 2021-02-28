package com.juno.weather.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.juno.weather.R
import com.juno.weather.databinding.ActivityMainBinding
import com.juno.weather.ui.main.favorite.FavoriteFragment
import com.juno.weather.ui.main.search.SearchFragment
import com.juno.weather.ui.main.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> setFragment(searchFragment)
                R.id.menu_favorite -> setFragment(favoriteFragment)
                R.id.menu_settings -> setFragment(settingsFragment)
            }

            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.menu_search
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.containerView.id, fragment)
            .addToBackStack(null)
            .commit()
    }
}