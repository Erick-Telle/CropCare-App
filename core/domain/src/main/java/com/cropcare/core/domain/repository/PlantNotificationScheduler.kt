package com.cropcare.core.domain.repository

interface PlantNotificationScheduler {
    fun schedule(
        plantId: Long,
        plantNombre: String,
        cantidadAguaMl: Int,
        triggerAtMillis: Long
    )

    fun cancel(plantId: Long)
}
