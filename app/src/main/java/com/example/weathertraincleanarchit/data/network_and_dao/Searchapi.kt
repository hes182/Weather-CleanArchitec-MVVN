package com.example.weathertraincleanarchit.data.network_and_dao

import com.example.weathertraincleanarchit.data.SearchModel.SearchResults_Dto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Searchapi {
    @GET("/v1/search?count=10&language=en&format=json")
    suspend fun GetSearchresults(
        @Query("name")
        cityName: String
    ) : Response<SearchResults_Dto>
}