package com.example.weathertraincleanarchit.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weathertraincleanarchit.data.network_and_dao.Weatherapi
import com.example.weathertraincleanarchit.domain.model.CurrentWeather
import com.example.weathertraincleanarchit.domain.model.Daily
import com.example.weathertraincleanarchit.domain.model.Hourly
import com.example.weathertraincleanarchit.domain.model.Mapper.mapDailyWeatherByDayOfWeek
import com.example.weathertraincleanarchit.domain.model.Mapper.toDailyWeatherList
import com.example.weathertraincleanarchit.domain.model.Mapper.toDomainModel
import com.example.weathertraincleanarchit.domain.model.Mapper.toHourlyWeatherList
import com.example.weathertraincleanarchit.domain.repository.WeatherRepo
import com.example.weathertraincleanarchit.utils.SafeApiRequest
import java.time.DayOfWeek
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val weatherapiService: Weatherapi
) : WeatherRepo, SafeApiRequest(){
    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather {
        val response_currentWeather = safeApiRequest {
            Log.d("Repository", "Fetching Current Weather Data")
            weatherapiService.getAllWeatherData(lat, lon)
        }
        val apiCurrenWeather_Dto = response_currentWeather.current_weather
        Log.d("Repository", "Current Weather data receive : $apiCurrenWeather_Dto")

        val domainCurrentWeather = apiCurrenWeather_Dto.toDomainModel()
        return domainCurrentWeather
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getHourlyWeather(lat: Double, lon: Double): List<Hourly> {
       val response_hourly = safeApiRequest {
           weatherapiService.getAllWeatherData(lat, lon)
       }
        val hourly_weather = response_hourly.hourly
        val hourlyWeatherList = hourly_weather.toHourlyWeatherList()
        return hourlyWeatherList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDailyWeather(lat: Double, lon: Double): Map<DayOfWeek, List<Daily>> {
        val response_daily = safeApiRequest {
            Log.d("Repository1", "Fetching current weather data ")
            weatherapiService.getAllWeatherData(lat, lon)
        }
        val daily_weather = response_daily.daily
        val dailyWeatherList = daily_weather.toDailyWeatherList()
        Log.d("Repository1", "Daily weather data received: $dailyWeatherList")

        val dailyWetherMap = mapDailyWeatherByDayOfWeek(dailyWeatherList)
        return dailyWetherMap
    }
}