package com.example.calendarmobileapp.remote

import com.example.calendarmobileapp.data.constant.URK_weather_endpoint
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {
    @GET(URK_weather_endpoint)
    fun getWeather(): Call<WeatherResponse>
}