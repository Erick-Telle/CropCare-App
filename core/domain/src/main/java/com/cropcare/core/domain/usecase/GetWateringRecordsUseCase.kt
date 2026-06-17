package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWateringRecordsUseCase @Inject constructor(
    private val wateringRepository: WateringRepository
) {
    operator fun invoke(plantId: Long): Flow<List<WateringRecord>> =
        wateringRepository.getRecordsByPlantId(plantId)
}
