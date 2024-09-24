package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.service.api.NetworkResponse
import com.example.weatherapp.service.api.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherRepository = WeatherRepository()

    private val _weather = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weather: LiveData<NetworkResponse<WeatherModel>> = _weather

    fun getData(city: String) {
        _weather.value = NetworkResponse.Loading

        try {
            viewModelScope.launch {
                val response = weatherRepository.get(city)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _weather.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weather.value = NetworkResponse.Failure("Failed to load data: $_weather")
                }
            }
        } catch (error: Exception) {
            _weather.value = NetworkResponse.Failure("Failed to load data: ${error.message}")
        }
    }
}