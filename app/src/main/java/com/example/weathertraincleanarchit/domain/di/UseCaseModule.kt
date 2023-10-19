package com.example.weathertraincleanarchit.domain.di

import com.example.weathertraincleanarchit.data.repository.SearchRepoImpl
import com.example.weathertraincleanarchit.data.repository.WeatherRepoImpl
import com.example.weathertraincleanarchit.domain.repository.WeatherRepo
import com.example.weathertraincleanarchit.domain.usecases.DeleteCityUseCase
import com.example.weathertraincleanarchit.domain.usecases.GetCurrentWeather
import com.example.weathertraincleanarchit.domain.usecases.GetDailyWeather
import com.example.weathertraincleanarchit.domain.usecases.GetHourlyWeather
import com.example.weathertraincleanarchit.domain.usecases.GetSearchResults
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetCurrentWeather(weatherRepoImpl: WeatherRepoImpl) : GetCurrentWeather {
        return GetCurrentWeather(weatherRepoImpl)
    }

    @Provides
    fun provideGetDailyWeather(weatherRepoImpl: WeatherRepoImpl) : GetDailyWeather {
        return GetDailyWeather(weatherRepoImpl)
    }

    @Provides
    fun provideGetHourlyWeather(weatherRepoImpl: WeatherRepoImpl) : GetHourlyWeather {
        return GetHourlyWeather(weatherRepoImpl)
    }

    @Provides
    fun provideSearchResults(searchRepoImpl: SearchRepoImpl) : GetSearchResults {
        return GetSearchResults(searchRepoImpl)
    }

    @Provides
    fun provideDeleteUseCAse(searchRepoImpl: SearchRepoImpl) : DeleteCityUseCase {
        return DeleteCityUseCase(searchRepoImpl)
    }
}