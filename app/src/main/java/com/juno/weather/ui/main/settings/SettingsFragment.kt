package com.juno.weather.ui.main.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.juno.weather.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_settings_screen)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)

        sharedPreference.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == getString(R.string.mode)) {
                sharedPreferences.getString(getString(R.string.mode), getString(R.string.mode_default_key))
            }
        }
    }
}