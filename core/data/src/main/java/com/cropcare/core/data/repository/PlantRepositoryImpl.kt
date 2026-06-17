package com.cropcare.core.data.repository

import com.cropcare.core.data.local.dao.PlantDao
import com.cropcare.core.data.mapper.toDomain
import com.cropcare.core.data.mapper.toEntity
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: PlantDao
) : PlantRepository {

    override fun getAllPlants(): Flow<List<Plant>> =
        plantDao.getAllPlants().map { entities ->
            entities.map { it.toDomain(WateringStatus.AL_DIA) }
        }

    override fun getPlantById(id: Long): Flow<Plant?> =
        plantDao.getPlantById(id).map { entity ->
            entity?.toDomain(WateringStatus.AL_DIA)
        }

    override suspend fun insertPlant(plant: Plant): Long =
        plantDao.insertPlant(plant.toEntity())

    override suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant.toEntity())
    }

    override suspend fun deletePlant(plant: Plant) {
        plantDao.deletePlant(plant.toEntity())
    }
}
