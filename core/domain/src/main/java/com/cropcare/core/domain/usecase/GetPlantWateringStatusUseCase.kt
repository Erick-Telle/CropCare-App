package com.cropcare.core.domain.usecase

import com.cropcare.core.domain.model.WateringStatus
import java.util.Calendar
import javax.inject.Inject

class GetPlantWateringStatusUseCase @Inject constructor(
    private val getNextWateringDateUseCase: GetNextWateringDateUseCase
) {

    operator fun invoke(ultimaFechaRiego: Long, frecuenciaRiegoDias: Int): WateringStatus {
        val proximaFechaRiego = getNextWateringDateUseCase(ultimaFechaRiego, frecuenciaRiegoDias)
        val diasRestantes = calculateDaysRemaining(proximaFechaRiego)

        return when {
            diasRestantes < 0 -> WateringStatus.ATRASADA
            diasRestantes == 0 -> WateringStatus.PENDIENTE
            else -> WateringStatus.AL_DIA
        }
    }

    fun calculateDaysRemaining(proximaFechaRiego: Long): Int {
        val today = startOfDay(Calendar.getInstance())
        val nextDay = startOfDay(Calendar.getInstance().apply { timeInMillis = proximaFechaRiego })
        return ((nextDay - today) / GetNextWateringDateUseCase.MILLIS_PER_DAY).toInt()
    }

    private fun startOfDay(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}
