package com.cropcare.core.domain.repository

import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.WateringRecord
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<Plant>>
    fun getPlantById(id: Long): Flow<Plant?>
    suspend fun insertPlant(plant: Plant): Long
    suspend fun updatePlant(plant: Plant)
    suspend fun deletePlant(plant: Plant)
}

interface SpeciesRepository {
    fun getAllSpecies(): Flow<List<Species>>
    fun getSpeciesById(id: Long): Flow<Species?>
    fun searchSpeciesByName(query: String): Flow<List<Species>>
}

interface WateringRepository {
    fun getRecordsByPlantId(plantId: Long): Flow<List<WateringRecord>>
    suspend fun insertRecord(record: WateringRecord): Long
}

interface ClimateRepository {
    fun getClimateConfig(): Flow<ClimateConfig?>
    suspend fun saveClimateConfig(config: ClimateConfig)
}
