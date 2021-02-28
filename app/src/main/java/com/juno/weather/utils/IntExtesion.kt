package com.juno.weather.utils

import android.content.res.Resources

fun Int.toPX() = (this * Resources.getSystem().displayMetrics.density).toInt()