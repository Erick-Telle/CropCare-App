package com.cropcare.feature.plants.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.ui.charts.BarChartLabels
import com.cropcare.core.ui.charts.DonutChart
import com.cropcare.core.ui.charts.DonutSegment
import com.cropcare.core.ui.charts.SimpleBarChart
import com.cropcare.core.ui.components.ChartCard
import com.cropcare.core.ui.components.StatsMiniCardItem
import com.cropcare.core.ui.components.StatsMiniCardRow
import com.cropcare.core.ui.theme.StatusOk
import com.cropcare.core.ui.theme.StatusOverdue
import com.cropcare.core.ui.theme.StatusPending
import java.util.Calendar

@Composable
fun DashboardStatsSection(
    allPlants: List<PlantWithSpecies>,
    modifier: Modifier = Modifier
) {
    val total = allPlants.size
    val alDia = allPlants.count { it.plantWithStatus.status == WateringStatus.AL_DIA }
    val pendiente = allPlants.count { it.plantWithStatus.status == WateringStatus.PENDIENTE }
    val atrasada = allPlants.count { it.plantWithStatus.status == WateringStatus.ATRASADA }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Resumen",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        StatsMiniCardRow {
            StatsMiniCardItem(
                label = "Total plantas",
                value = total.toString(),
                icon = Icons.Default.LocalFlorist
            )
            // TODO: Exponer totalWateringsMes desde GetAppUsageSummaryUseCase o agregado en DashboardViewModel
            StatsMiniCardItem(
                label = "Riegos este mes",
                value = "—",
                icon = Icons.Default.WaterDrop
            )
            // TODO: Exponer rachaConstanciaDias calculada desde historial de riegos sin atrasos
            StatsMiniCardItem(
                label = "Racha",
                value = "—",
                icon = Icons.Default.WaterDrop
            )
        }

        ChartCard(
            title = "Riegos programados",
            subtitle = "Próximos 7 días por fecha de riego"
        ) {
            val dayLabels = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
            val counts = scheduledCountsByWeekday(allPlants)
            SimpleBarChart(labels = dayLabels, values = counts)
            Spacer(modifier = Modifier.height(8.dp))
            BarChartLabels(labels = dayLabels)
        }

        ChartCard(
            title = "Estado de plantas",
            subtitle = "Distribución actual"
        ) {
            DonutChart(
                segments = listOf(
                    DonutSegment("Al día", alDia, StatusOk),
                    DonutSegment("Pendiente", pendiente, StatusPending),
                    DonutSegment("Atrasada", atrasada, StatusOverdue)
                ),
                centerLabel = "Plantas",
                centerValue = total.toString()
            )
        }

        // TODO: Gráfica de riegos completados por día requiere GetWateringHistoryUseCase agregado en DashboardViewModel
        if (total == 0) {
            Text(
                text = "Agrega plantas para ver estadísticas detalladas.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

private fun scheduledCountsByWeekday(plants: List<PlantWithSpecies>): List<Float> {
    val calendar = Calendar.getInstance()
    val counts = FloatArray(7)
    plants.forEach { item ->
        calendar.timeInMillis = item.plantWithStatus.proximaFechaRiego
        val dayIndex = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
        counts[dayIndex] += 1f
    }
    return counts.toList()
}
