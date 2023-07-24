package com.example.lostfound.utils

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.widget.Toast
import androidx.core.content.edit
import com.example.lostfound.core.MyApplication
import com.mapbox.geojson.Point
import com.mapbox.geojson.Point.fromLngLat

class PreferenceManager private constructor(context: Context){
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private var instance: PreferenceManager? = null

        fun getInstance(context: Context): PreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: PreferenceManager(context.applicationContext).also{
                    instance = it
                }
            }
        }
    }

    fun saveLocation(location: Location): Boolean{
        sharedPreferences.edit {
            putFloat(Constants.LATITUDE, location.latitude.toFloat())
            putFloat(Constants.LONGITUDE, location.longitude.toFloat())
        }
        return true
    }

    fun saveLocation(point: Point): Boolean{
        sharedPreferences.edit {
            putFloat(Constants.LATITUDE, point.latitude().toFloat())
            putFloat(Constants.LONGITUDE, point.longitude().toFloat())
        }
        println(point)
        println(getLocationPoint().toString())
        return true
    }

    fun getLocationPoint(): Point{
        return fromLngLat(sharedPreferences.getFloat(Constants.LONGITUDE, 0.0f).toDouble(),
            sharedPreferences.getFloat(Constants.LATITUDE, 0.0f).toDouble())
    }
}