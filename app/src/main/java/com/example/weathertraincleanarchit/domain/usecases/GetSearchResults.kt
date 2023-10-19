package com.example.weathertraincleanarchit.domain.usecases

import com.example.weathertraincleanarchit.data.SearchModel.CityEntity
import com.example.weathertraincleanarchit.data.repository.SearchRepoImpl
import com.example.weathertraincleanarchit.domain.searchResultModel.Search_Results
import com.example.weathertraincleanarchit.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResults @Inject constructor(
    private val repoImpl: SearchRepoImpl
) {
    operator fun invoke(cityName: String): Flow<Resource<List<Search_Results>>> = flow {
        try {
            emit(Resource.Success(repoImpl.GetSearchResult(cityName)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    suspend operator fun invoke(city: CityEntity) {
        repoImpl.insertCity(city)
    }

    operator fun invoke() : Flow<List<CityEntity>> {
        return repoImpl.getAllCities()
    }
}