package com.example.weathertraincleanarchit.domain.usecases

import com.example.weathertraincleanarchit.data.repository.WeatherRepoImpl
import com.example.weathertraincleanarchit.domain.model.CurrentWeather
import com.example.weathertraincleanarchit.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(
    private val weatherRepoImpl: WeatherRepoImpl
) {
    operator fun invoke(lat: Double, lon: Double) : Flow<Resource<CurrentWeather>> = flow {
        try {
            emit(Resource.Success(weatherRepoImpl.getCurrentWeather(lat, lon)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}