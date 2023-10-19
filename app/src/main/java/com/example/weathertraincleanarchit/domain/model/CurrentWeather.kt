package com.example.weathertraincleanarchit.domain.model

data class CurrentWeather (
    val isDay: Int,
    val temperature: Double,
    val time: String,
    val weatherType: WeatherType,
    val windDirection: Int,
    val windSpeed: Double
)