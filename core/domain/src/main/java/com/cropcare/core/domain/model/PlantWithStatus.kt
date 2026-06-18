package com.cropcare.core.domain.model

data class PlantWithStatus(
    val plant: Plant,
    val status: WateringStatus,
    val proximaFechaRiego: Long,
    val diasRestantes: Int
)
