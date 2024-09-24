package com.example.weatherapp.service.api.repository

import com.example.weatherapp.constants.WeatherConstants
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.service.api.RetrofitInstance
import retrofit2.Response

class WeatherRepository {
    private val weatherApi = RetrofitInstance.weatherApi

    suspend fun get(apiKey: String): Response<WeatherModel> {
        return weatherApi.getWeather(apiKey, WeatherConstants.API_KEY)
    }
}