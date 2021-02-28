package com.juno.weather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

@SuppressLint("ObsoleteSdkInt")
@Suppress("DEPRECATION")
fun Context.isInternetAvailable(): Boolean {
    var result = false

    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            result = hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            result = type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE
        }
    }

    return result
}