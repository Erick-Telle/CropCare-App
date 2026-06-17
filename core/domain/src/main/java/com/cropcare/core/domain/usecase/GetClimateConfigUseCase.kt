package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.repository.ClimateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClimateConfigUseCase @Inject constructor(
    private val climateRepository: ClimateRepository
) {
    operator fun invoke(): Flow<ClimateConfig?> = climateRepository.getClimateConfig()
}
