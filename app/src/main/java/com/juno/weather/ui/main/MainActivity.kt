package com.juno.weather.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.juno.weather.R
import com.juno.weather.databinding.ActivityMainBinding
import com.juno.weather.ui.main.favorite.FavoriteFragment
import com.juno.weather.ui.main.search.SearchFragment
import com.juno.weather.ui.main.settings.SettingsFragment
import com.juno.weather.utils.FileUtils
import com.juno.weather.utils.KeyStorage

class MainActivity : AppCompatActivity() {
    companion object {
        private const val SAVE_FRAGMENT = "fragment"
    }

    private lateinit var binding: ActivityMainBinding

    private var searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private val settingsFragment = SettingsFragment()

    private val keyStorage by lazy { KeyStorage(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initUI()
        saveWeatherAPIKey()

        if (savedInstanceState != null) {
            val fragment = supportFragmentManager.getFragment(savedInstanceState, SAVE_FRAGMENT)

            if (fragment != null && fragment is SearchFragment) {
                searchFragment = fragment
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        supportFragmentManager.putFragment(outState, SAVE_FRAGMENT, searchFragment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
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

    private fun saveWeatherAPIKey() {
        keyStorage.save()
    }
}