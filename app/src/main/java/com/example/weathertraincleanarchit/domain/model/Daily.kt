package com.example.weathertraincleanarchit.domain.model

import java.time.LocalDate

data class Daily (
    val temperature_2m_max: Double,
    val temeprature_2m_min: Double,
    val time: LocalDate,
    val weatherType: WeatherType
)