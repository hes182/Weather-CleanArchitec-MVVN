package com.example.weathertraincleanarchit.domain.repository

import com.example.weathertraincleanarchit.data.SearchModel.CityEntity
import com.example.weathertraincleanarchit.domain.searchResultModel.Search_Results
import kotlinx.coroutines.flow.Flow

interface ResultsRepo {
    suspend fun GetSearchResult(cityName: String) : List<Search_Results>

    // city management function
    suspend fun insertCity(city: CityEntity)
    suspend fun deleteCity(city: CityEntity)
    fun getAllCities(): Flow<List<CityEntity>>
}