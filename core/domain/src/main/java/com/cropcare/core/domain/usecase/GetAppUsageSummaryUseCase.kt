package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

data class AppUsageSummary(
    val totalPlants: Int,
    val totalWaterings: Int
)

class GetAppUsageSummaryUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val wateringRepository: WateringRepository
) {
    suspend operator fun invoke(): AppUsageSummary {
        val plants = plantRepository.getAllPlants().first()
        var totalWaterings = 0
        plants.forEach { plant ->
            val records = wateringRepository.getRecordsByPlantId(plant.id).first()
            totalWaterings += records.size
        }
        return AppUsageSummary(
            totalPlants = plants.size,
            totalWaterings = totalWaterings
        )
    }
}
