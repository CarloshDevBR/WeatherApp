package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    fun getData(city: String) {
        Log.i("Log teste", city)
    }
}