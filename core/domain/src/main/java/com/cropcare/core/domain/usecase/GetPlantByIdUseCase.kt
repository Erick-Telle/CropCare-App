package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantByIdUseCase @Inject constructor(
    private val plantRepository: PlantRepository
) {
    operator fun invoke(id: Long): Flow<Plant?> = plantRepository.getPlantById(id)
}
