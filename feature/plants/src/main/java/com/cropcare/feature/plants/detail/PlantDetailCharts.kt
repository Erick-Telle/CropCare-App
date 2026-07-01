package com.cropcare.feature.plants.detail

import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.ui.charts.LineChartPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val DAY_MS = 86_400_000L

fun computeWateringProgress(plant: Plant, records: List<WateringRecord>): Float {
    val frequency = plant.frecuenciaRiegoDias.coerceAtLeast(1)
    val lastWatering = records.firstOrNull()?.timestamp ?: plant.fechaCreacion
    val elapsedDays = ((System.currentTimeMillis() - lastWatering) / DAY_MS).toFloat()
    return (elapsedDays / frequency).coerceIn(0f, 1f)
}

fun wateringLinePoints(records: List<WateringRecord>): List<LineChartPoint> {
    val formatter = SimpleDateFormat("dd/MM", Locale("es", "ES"))
    return records
        .sortedBy { it.timestamp }
        .map { record ->
            LineChartPoint(
                label = formatter.format(Date(record.timestamp)),
                value = record.cantidadAguaMl.toFloat()
            )
        }
}

fun wateringHeatmapDays(records: List<WateringRecord>, days: Int = 30): Set<Int> {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val todayStart = calendar.timeInMillis

    val watered = mutableSetOf<Int>()
    records.forEach { record ->
        calendar.timeInMillis = record.timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val recordDay = calendar.timeInMillis
        val daysAgo = ((todayStart - recordDay) / DAY_MS).toInt()
        if (daysAgo in 0 until days) {
            watered.add(days - 1 - daysAgo)
        }
    }
    return watered
}
