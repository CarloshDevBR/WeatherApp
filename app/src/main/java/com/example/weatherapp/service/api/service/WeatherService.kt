package com.example.weatherapp.service.api.service

import com.example.weatherapp.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): Response<WeatherModel>

}