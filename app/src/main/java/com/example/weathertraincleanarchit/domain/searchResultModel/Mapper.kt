package com.example.weathertraincleanarchit.domain.searchResultModel

import com.example.weathertraincleanarchit.data.SearchModel.CityEntity

object Mapper2 {
    fun mapToCityEntity(searchResult: Search_Results) : CityEntity {
        return CityEntity(
            name = searchResult.name,
            population = searchResult.population,
            timezone = searchResult.timezone,
            country = searchResult.country,
            admin1 = searchResult.admin1,
            latitude = searchResult.latitude,
            longitude = searchResult.longitude
        )
    }
}