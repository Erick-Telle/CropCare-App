package com.cropcare.core.data.remote.api

import com.cropcare.core.data.remote.dto.SpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpeciesApiService {

    @GET("api/species")
    suspend fun getAllSpecies(): List<SpeciesDto>

    @GET("api/species/{id}")
    suspend fun getSpeciesById(@Path("id") id: Long): SpeciesDto

    @GET("api/species/search")
    suspend fun searchSpecies(@Query("nombre") nombre: String): List<SpeciesDto>
}
