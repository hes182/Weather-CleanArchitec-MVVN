package com.example.weathertraincleanarchit.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertraincleanarchit.data.SearchModel.CityEntity
import com.example.weathertraincleanarchit.domain.usecases.DeleteCityUseCase
import com.example.weathertraincleanarchit.domain.usecases.GetSearchResults
import com.example.weathertraincleanarchit.presentation.state.SearchResultsState
import com.example.weathertraincleanarchit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val getSearchResults: GetSearchResults,
    private val deleteCityUseCase: DeleteCityUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    private val _state = MutableStateFlow(SearchResultsState())
    var state = _state.asStateFlow()


    var selectedCity = mutableStateOf<CityEntity?>(null)
        private set

    var switchState by mutableStateOf(true)
        internal set

    var selectedLatitude = mutableStateOf<Double?>(null)
        private set

    var selectedLongitude = mutableStateOf<Double?>(null)
        private set

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        fetchSearchResults(text)
    }

    fun toogleSwitchState(b: Boolean) {
        selectedLatitude.value = null
        selectedLongitude.value = null

        switchState = b
    }

    private fun fetchSearchResults(query: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(data = null, isLoading = true, error = null)
                val searchResultFlow = getSearchResults(query)
                searchResultFlow.collect() { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val data = resource.data

                            _state.value = _state.value.copy(data = data, isLoading = false, error = null)
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(data = null, isLoading = false, error = "Can't fetch")
                        }
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(data = null, isLoading = false, error = e.message)
            }
        }
    }

    fun insertCity(city: CityEntity) {
        viewModelScope.launch {
            getSearchResults(city)
        }
    }

    /** Delete Data City Di Room DB */
    fun deleteCity(city: CityEntity) {
        viewModelScope.launch {
            deleteCityUseCase(city)
        }
    }

    val allCities: Flow<List<CityEntity>> = getSearchResults()
}