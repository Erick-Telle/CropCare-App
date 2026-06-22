package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.repository.PlantNotificationScheduler
import javax.inject.Inject

class CancelPlantNotificationUseCase @Inject constructor(
    private val plantNotificationScheduler: PlantNotificationScheduler
) {
    operator fun invoke(plantId: Long) {
        plantNotificationScheduler.cancel(plantId)
    }
}
