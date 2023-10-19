package com.example.weathertraincleanarchit.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weathertraincleanarchit.data.repository.WeatherRepoImpl
import com.example.weathertraincleanarchit.domain.model.Daily
import com.example.weathertraincleanarchit.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.DayOfWeek
import javax.inject.Inject

class GetDailyWeather @Inject constructor(
    private val weatherRepoImpl: WeatherRepoImpl
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(lat: Double, lon: Double) : Flow<Resource<Map<DayOfWeek, List<Daily>>>> = flow {
        try {
            emit(Resource.Success(weatherRepoImpl.getDailyWeather(lat,lon)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

}