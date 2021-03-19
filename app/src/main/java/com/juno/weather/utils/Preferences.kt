package com.juno.weather.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.juno.weather.R

class Preferences(private val context: Context) {
    private var sharedPreference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getUnit(): String? {
        val defaultValue = context.resources.getString(R.string.temperature_unit_metric_key);

        return sharedPreference.getString("preference_temperature_unit", defaultValue)
    }

    fun getLanguage(): String? {
        val defaultValue = context.resources.getString(R.string.language_english_key);

        return sharedPreference.getString("preference_language", defaultValue)
    }

    fun getUnitLabel(): String? {
        val unit = getUnit()

        return context.resources.getString(if(unit == "metric") { R.string.temperature_unit_metric } else { R.string.temperature_unit_imperial })
    }

    fun getOffline(): Boolean {
        return sharedPreference.getBoolean("preference_offline", false)
    }
}