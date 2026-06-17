package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlantsUseCase @Inject constructor(
    private val plantRepository: PlantRepository
) {
    operator fun invoke(): Flow<List<Plant>> = plantRepository.getAllPlants()
}
