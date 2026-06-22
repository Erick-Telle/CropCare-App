package com.cropcare.core.domain.usecase

import javax.inject.Inject

class GetNextWateringDateUseCase @Inject constructor() {

    operator fun invoke(ultimaFechaRiego: Long, frecuenciaRiegoDias: Int): Long =
        ultimaFechaRiego + frecuenciaRiegoDias * MILLIS_PER_DAY

    companion object {
        const val MILLIS_PER_DAY = 86_400_000L
    }
}
