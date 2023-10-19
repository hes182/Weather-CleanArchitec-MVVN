package com.example.weathertraincleanarchit.presentation.composables

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import com.example.weathertraincleanarchit.presentation.navigation.NavScreen
import com.example.weathertraincleanarchit.presentation.viewmodels.CurrentWeatherViewModel
import com.example.weathertraincleanarchit.presentation.viewmodels.DailyWeatherViewModel
import com.example.weathertraincleanarchit.presentation.viewmodels.HourlyWeatherViewModel
import com.example.weathertraincleanarchit.presentation.viewmodels.SearchCityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllWeatherComposables(
    navController: NavHostController,
    viewModel: CurrentWeatherViewModel,
    dailyWeatherViewModel: DailyWeatherViewModel,
    hourlyWeatherViewModel: HourlyWeatherViewModel,
    searchCityViewModel: SearchCityViewModel,
    handle: SavedStateHandle
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    val state = viewModel.state
    val state1 = dailyWeatherViewModel.state
    var permissionStatus by remember { mutableStateOf(false) }

    var selectedLatitude = searchCityViewModel.selectedLatitude.value
    var selectedLongitude = searchCityViewModel.selectedLongitude.value
    var switcState = searchCityViewModel.switchState

    var refreshWeather by remember { mutableStateOf(false) }

    permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {permission ->
        val granted = permission[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            permissionStatus = true
        }
    }

    LaunchedEffect(permissionStatus) {
        if (!permissionStatus) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(switcState, refreshWeather, selectedLatitude, selectedLongitude, permissionStatus) {
        if (refreshWeather || selectedLatitude != null && selectedLongitude != null) {
            viewModel.fetchCurrentWeather(selectedLatitude, selectedLongitude)
            dailyWeatherViewModel.fetchDailyWeather(selectedLatitude, selectedLongitude)
            hourlyWeatherViewModel.fetchHourlyWeather(selectedLatitude, selectedLongitude)
        } else if (refreshWeather || permissionStatus || switcState) {
            viewModel.fetchCurrentWeather()
            dailyWeatherViewModel.fetchDailyWeather()
            hourlyWeatherViewModel.fetchHourlyWeather()
        }
        refreshWeather = false
    }

    if (state.isLoading) {
        CircularProgressBar()
    } else if (state.error != null) {
        Text(
            text = state.error,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = {},
                    actions = {
                        IconButton(onClick = {navController.navigate(NavScreen.Locations.route)}) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "setting"
                            )
                        }
                        IconButton(onClick = { refreshWeather = true }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "refresh"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                item {
                    Spacer(modifier = Modifier.height(180.dp))
                    CurrentWeatherCard(
                        state = state,
                        state1 = state1,
                        modifier = Modifier,
                        currentWatherVM = viewModel
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HourlyWeatherComposables(state = hourlyWeatherViewModel.state, modefier = Modifier)
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(70.dp)
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}