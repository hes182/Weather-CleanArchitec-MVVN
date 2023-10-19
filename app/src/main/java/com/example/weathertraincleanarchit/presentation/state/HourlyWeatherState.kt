package com.example.weathertraincleanarchit.presentation.state

import com.example.weathertraincleanarchit.domain.model.Hourly

data class HourlyWeatherState (
    val isLoading: Boolean = false,
    val data : List<Hourly>? = null,
    val error: String? = null
)