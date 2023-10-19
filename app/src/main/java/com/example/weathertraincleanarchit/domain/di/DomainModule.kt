package com.example.weathertraincleanarchit.domain.di

import com.example.weathertraincleanarchit.data.network_and_dao.CityDao
import com.example.weathertraincleanarchit.data.network_and_dao.Searchapi
import com.example.weathertraincleanarchit.data.network_and_dao.Weatherapi
import com.example.weathertraincleanarchit.data.repository.SearchRepoImpl
import com.example.weathertraincleanarchit.data.repository.WeatherRepoImpl
import com.example.weathertraincleanarchit.domain.repository.ResultsRepo
import com.example.weathertraincleanarchit.domain.repository.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherapi: Weatherapi
    ) : WeatherRepo {
        return WeatherRepoImpl(weatherapi)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchapi: Searchapi,
        cityDao: CityDao
    ) : ResultsRepo {
        return SearchRepoImpl(searchapi, cityDao)
    }
}