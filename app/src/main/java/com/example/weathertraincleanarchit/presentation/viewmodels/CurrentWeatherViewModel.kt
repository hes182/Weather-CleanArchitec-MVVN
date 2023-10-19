package com.example.weathertraincleanarchit.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertraincleanarchit.data.location.DefaultLocationTracker
import com.example.weathertraincleanarchit.domain.usecases.GetCurrentWeather
import com.example.weathertraincleanarchit.presentation.state.CurrentWeatherState
import com.example.weathertraincleanarchit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val context: Context,
    private val locationTracker: DefaultLocationTracker,
    private val getCurrentWeather: GetCurrentWeather
) : ViewModel() {
    private val _city = mutableStateOf("")
    var city: State<String> = _city
    var state by mutableStateOf(CurrentWeatherState())
        private set

    @SuppressLint("SuspiciousIndentation")
    fun fetchCurrentWeather(latitude: Double? = null, longitude: Double? = null) {
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
                _city.value = getCityName(location.first, location.second).toString()
                val weatherFlow = getCurrentWeather.invoke(location.first, location.second)
                weatherFlow.collect{
                    when (it){
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

    suspend fun getCityName(latitude: Double, longitude: Double) : String? {
        return withContext(Dispatchers.IO) {
            val geoCode = Geocoder(context, Locale.getDefault())
            try {
                val address = geoCode.getFromLocation(latitude, longitude, 1)
                if (address?.isNotEmpty() == true) {
                    val address = address[0]
                    val city = address.locality

                    city// Return City Name
                } else {
                    null// return null because no address found
                }
            }catch (e: Exception) {
                null
            }
        }
    }
}