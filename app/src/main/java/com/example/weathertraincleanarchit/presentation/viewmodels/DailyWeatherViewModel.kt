package com.example.weathertraincleanarchit.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertraincleanarchit.data.location.DefaultLocationTracker
import com.example.weathertraincleanarchit.domain.usecases.GetDailyWeather
import com.example.weathertraincleanarchit.presentation.state.DailyWeatherState
import com.example.weathertraincleanarchit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val locationTracker: DefaultLocationTracker,
    private val getDailyWeather: GetDailyWeather
) : ViewModel() {
    var state by mutableStateOf(DailyWeatherState())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchDailyWeather(latitude: Double? = null, longitude: Double? = null){
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            try {
                val location = if (latitude != null && longitude != null) {
                    Pair(latitude, longitude)
                } else {
                    val currentLocation = locationTracker.getLocation()
                    if (currentLocation != null) {
                        Pair(currentLocation.latitude, currentLocation.longitude)
                    } else {
                        state = state.copy(
                            isLoading = false,
                            data = null,
                            error = "Location not available"
                        )
                        return@launch
                    }
                }
                val weatherFlow = getDailyWeather.invoke(location.first, location.second)
                weatherFlow.collect{
                  when(it) {
                      is Resource.Success -> {
                          state = state.copy(
                              isLoading = false,
                              data = it.data,
                              error = null
                          )
                      }
                      is Resource.Error -> {
                          state = state.copy(
                              isLoading = false,
                              data = it.data,
                              error = null
                          )
                      }
                  }
                }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    data = null,
                    error = "Error fetching weather"
                )
            }
        }
    }

}