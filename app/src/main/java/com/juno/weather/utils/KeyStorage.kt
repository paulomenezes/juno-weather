package com.juno.weather.utils

import android.content.Context

class KeyStorage(private val context: Context) {
    private val API_KEY_FILE_NAME = "weather.txt"
    private val API_KEY = "0fd029d20a3a93a1ec448df0ba8edbd8"

    private val fileUtils by lazy { FileUtils(context) }

    fun save() {
        val file = read()

        if (file == null) {
            fileUtils.createFile(API_KEY_FILE_NAME, API_KEY)
        }
    }

    fun read(): String? {
        return fileUtils.readFile(API_KEY_FILE_NAME)
    }
}