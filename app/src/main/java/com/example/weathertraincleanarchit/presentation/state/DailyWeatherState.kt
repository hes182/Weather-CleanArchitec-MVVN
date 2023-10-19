package com.example.weathertraincleanarchit.presentation.state

import com.example.weathertraincleanarchit.domain.model.Daily
import java.time.DayOfWeek

data class DailyWeatherState (
    val isLoading : Boolean = false,
    val data : Map<DayOfWeek, List<Daily>>? = null,
    val error : String? = null,
    )