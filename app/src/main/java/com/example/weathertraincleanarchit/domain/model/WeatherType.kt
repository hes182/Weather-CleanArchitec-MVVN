package com.example.weathertraincleanarchit.domain.model

import androidx.annotation.DrawableRes
import com.example.weathertraincleanarchit.R

sealed class WeatherType (
    val weatherDesc: String,
    @DrawableRes val iconRes: Int,
) {
    object ClearSky : WeatherType(
        weatherDesc = "Clear Sky",
        iconRes = R.drawable.ic_sunny
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly Clear",
        iconRes = R.drawable.ic_cloudy
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly Cloudy",
        iconRes = R.drawable.ic_cloudy
    )
    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.ic_cloudy
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconRes = R.drawable.ic_verycloudy
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing Rime Fog",
        iconRes = R.drawable.ic_verycloudy
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Light Drizzle",
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Moderate Drizzle",
        iconRes = R.drawable.ic_rainshower
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Dense Drizzle",
        iconRes = R.drawable.ic_rainshower
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Slight Freezing Drizzle",
        iconRes = R.drawable.ic_snowyrain
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Dense Freezing Drizzle",
        iconRes = R.drawable.ic_snowyrain
    )
    object SlightRain : WeatherType(
        weatherDesc = "Slight Rain",
        iconRes = R.drawable.ic_rainy
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.ic_rainy
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy Rain",
        iconRes = R.drawable.ic_rainy
    )
    object HeavyFreezingRain : WeatherType(
        weatherDesc = "Heavy Freezing Rain",
        iconRes = R.drawable.ic_snowyrain
    )
    object SlightSnowFall : WeatherType(
        weatherDesc = "Slight Snow Fall",
        iconRes = R.drawable.ic_snowy
    )
    object ModerateSnowFall : WeatherType(
        weatherDesc = "Moderate Snow Fall",
        iconRes = R.drawable.ic_heavysnow
    )
    object HeavySnowFall : WeatherType(
        weatherDesc = "Heavy Snow Fall",
        iconRes = R.drawable.ic_heavysnow
    )
    object SnowGrains : WeatherType(
        weatherDesc = "Snow Grains",
        iconRes = R.drawable.ic_heavysnow
    )
    object SlightRainShowers : WeatherType(
        weatherDesc = "Moderate Rain Showers",
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateRainShowers : WeatherType(
        weatherDesc = "Moderate Rain Showers",
        iconRes = R.drawable.ic_rainshower
    )
    object ViolentRainShowers : WeatherType(
        weatherDesc = "Violent Rain Showers",
        iconRes = R.drawable.ic_rainshower
    )
    object SlightSnowShowers : WeatherType(
        weatherDesc = "Light Snow Showers",
        iconRes = R.drawable.ic_rainshower
    )
    object HeavySnowShowers : WeatherType(
        weatherDesc = "Heavy Snow Showers",
        iconRes = R.drawable.ic_snowy
    )
    object ModerateThunderstorm : WeatherType(
        weatherDesc = "Moderate Thunderstorm",
        iconRes = R.drawable.ic_rainythunder
    )
    object SlightHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm With Slight Hail",
        iconRes = R.drawable.ic_rainythunder
    )
    object HeavyHailThunderstorm : WeatherType(
        weatherDesc = "Thunderstorm With Heavy Hail",
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun fromWMO(code: Int) : WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}