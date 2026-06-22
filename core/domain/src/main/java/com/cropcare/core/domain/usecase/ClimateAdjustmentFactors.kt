package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Season

internal object ClimateAdjustmentFactors {

    fun temperatureMultiplier(temperature: Float): Float = when {
        temperature >= 35f -> 0.65f
        temperature >= 30f -> 0.75f
        temperature >= 25f -> 0.85f
        temperature >= 20f -> 1.0f
        temperature >= 15f -> 1.15f
        temperature >= 10f -> 1.25f
        else -> 1.35f
    }

    fun humidityMultiplier(humidity: Float): Float = when {
        humidity >= 70f -> 1.2f
        humidity >= 50f -> 1.0f
        humidity >= 30f -> 0.9f
        else -> 0.8f
    }

    fun humidityWaterMultiplier(humidity: Float): Float = when {
        humidity >= 70f -> 0.85f
        humidity >= 50f -> 1.0f
        humidity >= 30f -> 1.1f
        else -> 1.2f
    }

    fun seasonMultiplier(season: Season): Float = when (season) {
        Season.VERANO -> 0.85f
        Season.PRIMAVERA -> 0.95f
        Season.OTONO -> 1.05f
        Season.INVIERNO -> 1.15f
    }
}
