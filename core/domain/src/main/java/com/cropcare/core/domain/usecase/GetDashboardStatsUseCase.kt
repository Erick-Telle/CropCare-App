package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.DashboardStats
import com.cropcare.core.domain.model.PlantWithStatus
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.repository.PlantRepository
import com.cropcare.core.domain.repository.WateringRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetDashboardStatsUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val wateringRepository: WateringRepository,
    private val getAllPlantsWithStatusUseCase: GetAllPlantsWithStatusUseCase
) {
    operator fun invoke(): Flow<DashboardStats> =
        combine(
            getAllPlantsWithStatusUseCase(),
            allWateringRecordsFlow()
        ) { plantsWithStatus, allRecords ->
            computeStats(allRecords, plantsWithStatus)
        }

    private fun allWateringRecordsFlow(): Flow<List<WateringRecord>> =
        plantRepository.getAllPlants().flatMapLatest { plants ->
            if (plants.isEmpty()) {
                flowOf(emptyList())
            } else {
                val recordFlows = plants.map { plant ->
                    wateringRepository.getRecordsByPlantId(plant.id)
                }
                combine(recordFlows) { recordsArray ->
                    recordsArray.flatMap { it as List<WateringRecord> }
                }
            }
        }

    private fun computeStats(
        records: List<WateringRecord>,
        plantsWithStatus: List<PlantWithStatus>
    ): DashboardStats {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val wateringsThisMonth = records.count { record ->
            calendar.timeInMillis = record.timestamp
            calendar.get(Calendar.MONTH) == currentMonth &&
                calendar.get(Calendar.YEAR) == currentYear
        }

        val hasOverdue = plantsWithStatus.any { it.status == WateringStatus.ATRASADA }
        val streakDays = if (hasOverdue) {
            0
        } else {
            calculateStreak(records, calendar)
        }

        val last7Days = completedCountsLast7Days(records, calendar)

        return DashboardStats(
            wateringsThisMonth = wateringsThisMonth,
            streakDays = streakDays,
            completedWateringsLast7Days = last7Days
        )
    }

    private fun calculateStreak(
        records: List<WateringRecord>,
        calendar: Calendar
    ): Int {
        if (records.isEmpty()) return 0

        val wateringDays = records.map { record ->
            calendar.timeInMillis = record.timestamp
            calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR)
        }.toSet()

        var streak = 0
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        var checkDay = today.clone() as Calendar
        if (!wateringDays.contains(dayKey(checkDay))) {
            checkDay.add(Calendar.DAY_OF_YEAR, -1)
        }

        while (wateringDays.contains(dayKey(checkDay))) {
            streak++
            checkDay.add(Calendar.DAY_OF_YEAR, -1)
        }

        return streak
    }

    private fun dayKey(calendar: Calendar): Int =
        calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR)

    private fun completedCountsLast7Days(
        records: List<WateringRecord>,
        calendar: Calendar
    ): List<Int> {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        return (6 downTo 0).map { daysAgo ->
            val day = today.clone() as Calendar
            day.add(Calendar.DAY_OF_YEAR, -daysAgo)
            val key = dayKey(day)
            records.count { record ->
                calendar.timeInMillis = record.timestamp
                dayKey(calendar) == key
            }
        }
    }
}
