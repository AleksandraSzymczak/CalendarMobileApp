package com.example.calendarmobileapp.remote

data class WeatherResponse(
    val current: Current,
    val location: Location
)