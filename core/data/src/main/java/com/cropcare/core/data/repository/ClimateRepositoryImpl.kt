package com.cropcare.core.data.repository

import com.cropcare.core.data.local.dao.ClimateDao
import com.cropcare.core.data.mapper.toDomain
import com.cropcare.core.data.mapper.toEntity
import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.repository.ClimateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClimateRepositoryImpl @Inject constructor(
    private val climateDao: ClimateDao
) : ClimateRepository {

    override fun getClimateConfig(): Flow<ClimateConfig?> =
        climateDao.getClimateConfig().map { it?.toDomain() }

    override suspend fun saveClimateConfig(config: ClimateConfig) {
        climateDao.saveClimateConfig(config.toEntity())
    }
}
