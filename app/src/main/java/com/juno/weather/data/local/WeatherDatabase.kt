package com.juno.weather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.juno.weather.data.local.converters.DateConverter
import com.juno.weather.data.local.daos.FavoriteDAO
import com.juno.weather.data.local.daos.WeatherRequestDAO
import com.juno.weather.data.local.models.*

@Database(
    version = 7,
    entities = [
        Favorite::class,
        WeatherRequest::class,
        WeatherCity::class,
    ]
)
@TypeConverters(DateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    companion object {
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            if (instance == null) {
                synchronized(WeatherDatabase::class.java) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            WeatherDatabase::class.java,
                            "weather.db"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance!!
        }
    }

    abstract fun getFavorite(): FavoriteDAO

    abstract fun weatherRequestDAO(): WeatherRequestDAO
}