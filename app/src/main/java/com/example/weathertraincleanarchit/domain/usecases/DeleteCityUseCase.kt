package com.example.weathertraincleanarchit.domain.usecases

import com.example.weathertraincleanarchit.data.SearchModel.CityEntity
import com.example.weathertraincleanarchit.data.repository.SearchRepoImpl
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val repoImpl: SearchRepoImpl
) {
    suspend operator fun invoke(city: CityEntity) {
        repoImpl.deleteCity(city)
    }
}