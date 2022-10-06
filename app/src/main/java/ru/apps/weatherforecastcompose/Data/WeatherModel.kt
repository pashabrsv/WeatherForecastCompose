package ru.apps.weatherforecastcompose.Data

import java.util.concurrent.locks.Condition

data class WeatherModel(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val minTemp: String,
    val maxTemp: String,
    val icon: String,
    val hour: String
)
