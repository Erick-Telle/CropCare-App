package com.cropcare.feature.watering.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.EmptyStateView
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.components.StatsMiniCardItem
import com.cropcare.core.ui.components.StatsMiniCardRow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WateringHistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: WateringHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val timelineEntries = remember(uiState.records) { buildTimelineEntries(uiState.records) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CropCareTopAppBar(
                title = uiState.plantName.ifEmpty { "Historial de riegos" },
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.records.isEmpty() -> EmptyStateView(
                modifier = Modifier.padding(padding),
                title = "Sin registros de riego",
                message = "Cuando marques esta planta como regada, los riegos aparecerán aquí."
            )
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    StatsMiniCardRow {
                        StatsMiniCardItem(
                            label = "Total",
                            value = uiState.totalWaterings.toString(),
                            icon = Icons.Default.TouchApp
                        )
                        StatsMiniCardItem(
                            label = "Último",
                            value = uiState.lastWateringText.take(10),
                            icon = Icons.Default.Notifications
                        )
                        StatsMiniCardItem(
                            label = "Promedio",
                            value = "${uiState.averageWaterMl} ml",
                            icon = Icons.Default.TouchApp
                        )
                    }
                }

                items(timelineEntries, key = { it.key }) { entry ->
                    when (entry) {
                        is TimelineEntry.Header -> {
                            Text(
                                text = entry.monthLabel,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        is TimelineEntry.Record -> {
                            TimelineRecordItem(record = entry.record, isLast = entry.isLastInGroup)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineRecordItem(record: WateringRecord, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(72.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = formatRecordDate(record.timestamp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${record.cantidadAguaMl} ml",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = if (record.completadoPorNotificacion) "Vía notificación" else "Registro manual",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (record.notas != null) {
                Text(
                    text = record.notas!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private sealed class TimelineEntry {
    abstract val key: String

    data class Header(val monthLabel: String) : TimelineEntry() {
        override val key: String = "header_$monthLabel"
    }

    data class Record(
        val record: WateringRecord,
        val isLastInGroup: Boolean
    ) : TimelineEntry() {
        override val key: String = "record_${record.id}"
    }
}

private fun buildTimelineEntries(records: List<WateringRecord>): List<TimelineEntry> {
    val formatter = SimpleDateFormat("MMMM yyyy", Locale("es", "ES"))
    val grouped = records.groupBy {
        formatter.format(Date(it.timestamp)).replaceFirstChar { c ->
            c.titlecase(Locale("es", "ES"))
        }
    }
    return buildList {
        grouped.forEach { (monthLabel, monthRecords) ->
            add(TimelineEntry.Header(monthLabel))
            monthRecords.forEachIndexed { index, record ->
                add(
                    TimelineEntry.Record(
                        record = record,
                        isLastInGroup = index == monthRecords.lastIndex
                    )
                )
            }
        }
    }
}

private fun formatRecordDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "ES"))
    return formatter.format(Date(timestamp))
}
