package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWateringHistoryUseCase @Inject constructor(
    private val wateringRepository: WateringRepository
) {
    operator fun invoke(plantId: Long): Flow<List<WateringRecord>> =
        wateringRepository.getRecordsByPlantId(plantId).map { records ->
            records.sortedByDescending { it.timestamp }
        }
}
