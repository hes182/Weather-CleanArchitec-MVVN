package com.example.weathertraincleanarchit.data.repository

import android.util.Log
import com.example.weathertraincleanarchit.data.SearchModel.CityEntity
import com.example.weathertraincleanarchit.data.network_and_dao.CityDao
import com.example.weathertraincleanarchit.data.network_and_dao.Searchapi
import com.example.weathertraincleanarchit.domain.repository.ResultsRepo
import com.example.weathertraincleanarchit.domain.searchResultModel.Search_Results
import com.example.weathertraincleanarchit.utils.SafeApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val searchApi: Searchapi,
    private val cityDao: CityDao
) : ResultsRepo, SafeApiRequest(){
    override suspend fun GetSearchResult(cityName: String): List<Search_Results> {
        val searchResponse = safeApiRequest {
            searchApi.GetSearchresults(cityName)
        }
        val result = searchResponse.results.map {
            Search_Results(
                latitude = it?.latitude,
                longitude = it?.longitude,
                name = it?.name,
                population = it?.population,
                country = it?.country,
                timezone = it?.timezone,
                admin1 = it?.admin1
            )
        }
        return result
    }

    override suspend fun insertCity(city: CityEntity) {
        cityDao.insertCity(city)
    }

    override suspend fun deleteCity(city: CityEntity) {
        cityDao.deleteCity(city)
    }

    override fun getAllCities(): Flow<List<CityEntity>> {
        return cityDao.getAllCities()
    }
}