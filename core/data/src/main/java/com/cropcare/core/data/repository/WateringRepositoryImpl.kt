package com.cropcare.core.data.repository

import com.cropcare.core.data.local.dao.WateringDao
import com.cropcare.core.data.mapper.toDomain
import com.cropcare.core.data.mapper.toEntity
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WateringRepositoryImpl @Inject constructor(
    private val wateringDao: WateringDao
) : WateringRepository {

    override fun getRecordsByPlantId(plantId: Long): Flow<List<WateringRecord>> =
        wateringDao.getRecordsByPlantId(plantId).map { list -> list.map { it.toDomain() } }

    override suspend fun insertRecord(record: WateringRecord): Long =
        wateringDao.insertRecord(record.toEntity())
}
