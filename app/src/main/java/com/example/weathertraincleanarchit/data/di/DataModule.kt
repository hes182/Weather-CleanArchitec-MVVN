package com.example.weathertraincleanarchit.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.weathertraincleanarchit.data.network_and_dao.CityDao
import com.example.weathertraincleanarchit.data.network_and_dao.CityDatabase
import com.example.weathertraincleanarchit.data.network_and_dao.Searchapi
import com.example.weathertraincleanarchit.data.network_and_dao.Weatherapi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun WeatherApiService(): Weatherapi {
        return Retrofit.Builder().baseUrl("https://api.open-meteo.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(Weatherapi::class.java)
    }

    @Provides
    @Singleton
    fun SearchApiService() : Searchapi {
        return Retrofit.Builder().baseUrl("https://geocoding-api.open-meteo.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(Searchapi::class.java)
    }

    @Provides
    @Singleton
    fun provideFusedlocationClient(app:Application) : FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideSearchDatabse(@ApplicationContext context: Context): CityDatabase {
        return databaseBuilder(context, CityDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCityDao(appDatabse: CityDatabase) : CityDao {
        return appDatabse.cityDao()
    }
}