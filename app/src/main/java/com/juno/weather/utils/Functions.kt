package com.juno.weather.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showMessage(view: View, message: Int) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}
