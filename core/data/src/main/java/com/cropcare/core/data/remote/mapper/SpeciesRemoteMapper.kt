package com.cropcare.core.data.remote.mapper

import com.cropcare.core.data.local.entity.SpeciesEntity
import com.cropcare.core.data.remote.dto.SpeciesDto
import com.cropcare.core.domain.model.Species

fun SpeciesDto.toDomain(): Species = Species(
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

fun SpeciesDto.toEntity(): SpeciesEntity = SpeciesEntity(
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

fun Species.toEntity(): SpeciesEntity = SpeciesEntity(
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
