package com.example.weathertraincleanarchit.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getLocation() : Location ?
}