package com.cropcare.feature.watering.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.EmptyStateView
import com.cropcare.core.ui.components.LoadingView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WateringHistoryScreen(
    onNavigateBack: () -> Unit,
    viewModel: WateringHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    SummaryCard(
                        totalWaterings = uiState.totalWaterings,
                        lastWateringText = uiState.lastWateringText,
                        averageWaterMl = uiState.averageWaterMl
                    )
                }
                items(uiState.records, key = { it.id }) { record ->
                    WateringRecordItem(record = record)
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(
    totalWaterings: Int,
    lastWateringText: String,
    averageWaterMl: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Resumen",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SummaryItem(label = "Total de riegos", value = totalWaterings.toString())
                SummaryItem(label = "Último riego", value = lastWateringText)
                SummaryItem(label = "Promedio", value = "$averageWaterMl ml")
            }
        }
    }
}

@Composable
private fun SummaryItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun WateringRecordItem(record: WateringRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (record.completadoPorNotificacion) {
                    Icons.Default.Notifications
                } else {
                    Icons.Default.TouchApp
                },
                contentDescription = if (record.completadoPorNotificacion) {
                    "Completado por notificación"
                } else {
                    "Completado manualmente"
                },
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = formatRecordDate(record.timestamp),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${record.cantidadAguaMl} ml",
                    style = MaterialTheme.typography.bodyMedium
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
}

private fun formatRecordDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "ES"))
    return formatter.format(Date(timestamp))
}
