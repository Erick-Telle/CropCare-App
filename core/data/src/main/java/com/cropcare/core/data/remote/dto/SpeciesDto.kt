package com.cropcare.core.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Modelo de red que refleja SpeciesResponseDTO de la API REST.
 * Separado de [com.cropcare.core.domain.model.Species].
 */
data class SpeciesDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nombreComun")
    val nombreComun: String,
    @SerializedName("nombreCientifico")
    val nombreCientifico: String,
    @SerializedName("frecuenciaBaseDias")
    val frecuenciaBaseDias: Int,
    @SerializedName("cantidadBaseAguaMl")
    val cantidadBaseAguaMl: Int,
    @SerializedName("ajustePorTemperatura")
    val ajustePorTemperatura: Float,
    @SerializedName("ajustePorHumedad")
    val ajustePorHumedad: Float,
    @SerializedName("consejosLuz")
    val consejosLuz: String,
    @SerializedName("consejosHumedad")
    val consejosHumedad: String,
    @SerializedName("consejosAbono")
    val consejosAbono: String,
    @SerializedName("fechaCreacion")
    val fechaCreacion: String? = null,
    @SerializedName("fechaActualizacion")
    val fechaActualizacion: String? = null
)
