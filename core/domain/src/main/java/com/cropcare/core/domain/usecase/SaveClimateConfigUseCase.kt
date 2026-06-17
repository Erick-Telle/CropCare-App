package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.ClimateConfig
import com.cropcare.core.domain.repository.ClimateRepository
import javax.inject.Inject

class SaveClimateConfigUseCase @Inject constructor(
    private val climateRepository: ClimateRepository
) {
    suspend operator fun invoke(config: ClimateConfig) {
        climateRepository.saveClimateConfig(config)
    }
}
