package com.example.weathertraincleanarchit.presentation.navigation

sealed class NavScreen(val name: String, val route: String) {
    object Home : NavScreen(name = "Home", route = "home")
    object Locations : NavScreen(name = "Locations", route = "location")
    object Search : NavScreen(name = "Seacrh", route = "search")
}