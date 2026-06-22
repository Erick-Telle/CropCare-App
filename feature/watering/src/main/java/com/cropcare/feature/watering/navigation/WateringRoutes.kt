package com.cropcare.feature.watering.navigation

object WateringRoutes {
    const val WATERING_HISTORY = "watering_history/{plantId}"
    const val ARG_PLANT_ID = "plantId"

    fun wateringHistoryRoute(plantId: Long) = "watering_history/$plantId"
}
