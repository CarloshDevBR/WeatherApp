package com.example.weatherapp.service.api

import com.example.weatherapp.constants.WeatherConstants
import com.example.weatherapp.service.api.service.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = WeatherConstants.BASE_URL

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherService = getInstance().create(WeatherService::class.java)
}