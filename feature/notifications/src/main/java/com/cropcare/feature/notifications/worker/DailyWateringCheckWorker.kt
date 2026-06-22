package com.cropcare.feature.notifications.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.usecase.GetAllPlantsWithStatusUseCase
import com.cropcare.feature.notifications.WateringNotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@HiltWorker
class DailyWateringCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val getAllPlantsWithStatusUseCase: GetAllPlantsWithStatusUseCase,
    private val notificationHelper: WateringNotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val plantsWithStatus = getAllPlantsWithStatusUseCase().first()

        plantsWithStatus
            .filter { it.status == WateringStatus.ATRASADA }
            .forEach { plantStatus ->
                val diasAtrasados = abs(plantStatus.diasRestantes)
                notificationHelper.showUrgentReminder(
                    plantId = plantStatus.plant.id,
                    plantNombre = plantStatus.plant.nombre,
                    cantidadAguaMl = plantStatus.plant.cantidadAguaMl,
                    diasAtrasados = diasAtrasados
                )
            }

        return Result.success()
    }

    companion object {
        private const val WORK_NAME = "daily_watering_check"

        fun schedule(context: Context) {
            val initialDelay = calculateInitialDelayTo8Am()

            val workRequest = PeriodicWorkRequestBuilder<DailyWateringCheckWorker>(
                24, TimeUnit.HOURS
            )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }

        private fun calculateInitialDelayTo8Am(): Long {
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (before(now) || equals(now)) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }
            return target.timeInMillis - now.timeInMillis
        }
    }
}
