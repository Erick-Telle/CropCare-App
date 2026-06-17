package com.cropcare.core.data.mapper

import com.cropcare.core.data.local.entity.ClimateConfigEntity
import com.cropcare.core.data.local.entity.PlantEntity
import com.cropcare.core.data.local.entity.SpeciesEntity
import com.cropcare.core.data.local.entity.WateringRecordEntity
import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.PlantLocation
import com.cropcare.core.domain.model.Season
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.model.WateringStatus

fun PlantEntity.toDomain(status: WateringStatus = WateringStatus.AL_DIA): Plant = Plant(
    id = id,
    nombre = nombre,
    especieId = especieId,
    ubicacion = PlantLocation.valueOf(ubicacion),
    fotoPath = fotoPath,
    fechaCreacion = fechaCreacion,
    frecuenciaRiegoDias = frecuenciaRiegoDias,
    cantidadAguaMl = cantidadAguaMl,
    activa = activa,
    estadoRiego = status
)

fun Plant.toEntity(): PlantEntity = PlantEntity(
    id = id,
    nombre = nombre,
    especieId = especieId,
    ubicacion = ubicacion.name,
    fotoPath = fotoPath,
    fechaCreacion = fechaCreacion,
    frecuenciaRiegoDias = frecuenciaRiegoDias,
    cantidadAguaMl = cantidadAguaMl,
    activa = activa
)

fun SpeciesEntity.toDomain(): Species = Species(
    id = id,
    nombreComun = nombreComun,
    nombreCientifico = nombreCientifico,
    frecuenciaBaseDias = frecuenciaBaseDias,
    cantidadBaseAguaMl = cantidadBaseAguaMl,
    ajustePorTemperatura = ajustePorTemperatura,
    ajustePorHumedad = ajustePorHumedad,
    consejosLuz = consejosLuz,
    consejosHumedad = consejosHumedad,
    consejosAbono = consejosAbono
)

fun WateringRecordEntity.toDomain(): WateringRecord = WateringRecord(
    id = id,
    plantId = plantId,
    timestamp = timestamp,
    cantidadAguaMl = cantidadAguaMl,
    completadoPorNotificacion = completadoPorNotificacion,
    notas = notas
)

fun WateringRecord.toEntity(): WateringRecordEntity = WateringRecordEntity(
    id = id,
    plantId = plantId,
    timestamp = timestamp,
    cantidadAguaMl = cantidadAguaMl,
    completadoPorNotificacion = completadoPorNotificacion,
    notas = notas
)

fun ClimateConfigEntity.toDomain(): ClimateConfig = ClimateConfig(
    id = id,
    temperaturaPromedio = temperaturaPromedio,
    humedadAmbiental = humedadAmbiental,
    estacionActual = Season.valueOf(estacionActual),
    fechaUltimaActualizacion = fechaUltimaActualizacion
)

fun ClimateConfig.toEntity(): ClimateConfigEntity = ClimateConfigEntity(
    id = id,
    temperaturaPromedio = temperaturaPromedio,
    humedadAmbiental = humedadAmbiental,
    estacionActual = estacionActual.name,
    fechaUltimaActualizacion = fechaUltimaActualizacion
)
