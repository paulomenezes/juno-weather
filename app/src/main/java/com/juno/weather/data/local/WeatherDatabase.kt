package com.juno.weather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.juno.weather.data.local.daos.FavoriteDAO
import com.juno.weather.data.local.models.Favorite

@Database(version = 1, entities = [Favorite::class])
abstract class WeatherDatabase : RoomDatabase() {
    companion object {
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            if (instance == null) {
                synchronized(WeatherDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        "weather.db"
                    ).allowMainThreadQueries().build()
                }
            }

            return instance!!
        }
    }

    abstract fun getFavorite(): FavoriteDAO
}