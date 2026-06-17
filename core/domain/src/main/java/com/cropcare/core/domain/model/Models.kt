package com.cropcare.core.domain.model

enum class PlantLocation {
    VENTANA,
    INTERIOR,
    EXTERIOR,
    BALCON
}

enum class Season {
    VERANO,
    OTONO,
    INVIERNO,
    PRIMAVERA
}

enum class WateringStatus {
    AL_DIA,
    PENDIENTE,
    ATRASADA
}

data class Plant(
    val id: Long = 0,
    val nombre: String,
    val especieId: Long,
    val ubicacion: PlantLocation,
    val fotoPath: String? = null,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val frecuenciaRiegoDias: Int,
    val cantidadAguaMl: Int,
    val activa: Boolean = true,
    val estadoRiego: WateringStatus = WateringStatus.AL_DIA
)

data class Species(
    val id: Long = 0,
    val nombreComun: String,
    val nombreCientifico: String,
    val frecuenciaBaseDias: Int,
    val cantidadBaseAguaMl: Int,
    val ajustePorTemperatura: Float,
    val ajustePorHumedad: Float,
    val consejosLuz: String,
    val consejosHumedad: String,
    val consejosAbono: String
)

data class WateringRecord(
    val id: Long = 0,
    val plantId: Long,
    val timestamp: Long,
    val cantidadAguaMl: Int,
    val completadoPorNotificacion: Boolean = false,
    val notas: String? = null
)

data class ClimateConfig(
    val id: Int = 1,
    val temperaturaPromedio: Float,
    val humedadAmbiental: Float,
    val estacionActual: Season,
    val fechaUltimaActualizacion: Long = System.currentTimeMillis()
)
