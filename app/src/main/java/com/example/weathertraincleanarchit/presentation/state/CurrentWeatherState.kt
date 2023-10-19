package com.example.weathertraincleanarchit.presentation.state

import com.example.weathertraincleanarchit.domain.model.CurrentWeather

data class CurrentWeatherState (
    val isLoading : Boolean = false,
    val data : CurrentWeather? = null,
    val error : String? = null
)