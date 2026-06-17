package com.cropcare.core.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val especieId: Long,
    val ubicacion: String,
    val fotoPath: String?,
    val fechaCreacion: Long,
    val frecuenciaRiegoDias: Int,
    val cantidadAguaMl: Int,
    val activa: Boolean
)

@Entity(tableName = "species")
data class SpeciesEntity(
    @PrimaryKey(autoGenerate = true)
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

@Entity(
    tableName = "watering_records",
    foreignKeys = [
        ForeignKey(
            entity = PlantEntity::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("plantId")]
)
data class WateringRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantId: Long,
    val timestamp: Long,
    val cantidadAguaMl: Int,
    val completadoPorNotificacion: Boolean,
    val notas: String?
)

@Entity(tableName = "climate_config")
data class ClimateConfigEntity(
    @PrimaryKey
    val id: Int = 1,
    val temperaturaPromedio: Float,
    val humedadAmbiental: Float,
    val estacionActual: String,
    val fechaUltimaActualizacion: Long
)
