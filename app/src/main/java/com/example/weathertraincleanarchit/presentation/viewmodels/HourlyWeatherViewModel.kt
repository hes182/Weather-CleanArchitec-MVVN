package com.example.weathertraincleanarchit.presentation.viewmodels

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertraincleanarchit.data.location.DefaultLocationTracker
import com.example.weathertraincleanarchit.domain.usecases.GetHourlyWeather
import com.example.weathertraincleanarchit.presentation.state.HourlyWeatherState
import com.example.weathertraincleanarchit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HourlyWeatherViewModel @Inject constructor(
    private val getHourlyWeather: GetHourlyWeather,
    private val locationTracker : DefaultLocationTracker
) : ViewModel() {
    var state by mutableStateOf(HourlyWeatherState())

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHourlyWeather(latitude: Double? = null, longitude: Double? = null) {
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
                            error = "Location not availabe"
                        )
                        return@launch
                    }
                }
                val weatherFlow = getHourlyWeather.invoke(location.first, location.second)

                weatherFlow.collect{ resource ->
                    when(resource) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                data = resource.data,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                data = resource.data,
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