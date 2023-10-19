package com.example.weathertraincleanarchit.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weathertraincleanarchit.presentation.composables.AllWeatherComposables
import com.example.weathertraincleanarchit.presentation.composables.LocationListComp
import com.example.weathertraincleanarchit.presentation.composables.SearchLocationComp
import com.example.weathertraincleanarchit.presentation.viewmodels.CurrentWeatherViewModel
import com.example.weathertraincleanarchit.presentation.viewmodels.DailyWeatherViewModel
import com.example.weathertraincleanarchit.presentation.viewmodels.HourlyWeatherViewModel
import com.example.weathertraincleanarchit.presentation.viewmodels.SearchCityViewModel

@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation( ) {
    val navController = rememberNavController()
    val savedStateHandle = remember {
        SavedStateHandle()
    }

    val searchcityViewmodel: SearchCityViewModel = hiltViewModel()
    val  currentWeatherViewModel: CurrentWeatherViewModel = hiltViewModel()
    val dailyweatherViewmodel: DailyWeatherViewModel = hiltViewModel()
    val  hourlyWeatherViewmodel: HourlyWeatherViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = NavScreen.Home.route){

        composable(NavScreen.Home.route){backStackEntry->
            AllWeatherComposables(
                navController = navController,
                viewModel = currentWeatherViewModel,
                dailyWeatherViewModel = dailyweatherViewmodel,
                hourlyWeatherViewModel = hourlyWeatherViewmodel,
                searchCityViewModel = searchcityViewmodel,
                handle = savedStateHandle
            )

        }

        composable(NavScreen.Locations.route){backStackEntry->
            LocationListComp(navController = navController, handle = savedStateHandle, searchCityViewModel = searchcityViewmodel)
        }


        composable(NavScreen.Search.route){backStackEntry->
            SearchLocationComp(navController = navController, viewModel = searchcityViewmodel)
        }

    }
}