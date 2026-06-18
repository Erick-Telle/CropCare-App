package com.cropcare.feature.settings.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.SecondaryButton
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralSettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToClimate: () -> Unit,
    viewModel: GeneralSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.showTimePicker) {
        val timePickerState = androidx.compose.material3.rememberTimePickerState(
            initialHour = uiState.reminderHour,
            initialMinute = uiState.reminderMinute,
            is24Hour = true
        )
        androidx.compose.material3.AlertDialog(
            onDismissRequest = viewModel::dismissTimePicker,
            title = { Text("Hora de recordatorio") },
            text = {
                androidx.compose.material3.TimePicker(state = timePickerState)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onTimeSelected(timePickerState.hour, timePickerState.minute)
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::dismissTimePicker) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CropCareTopAppBar(
                title = "Configuración",
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection(title = "Clima") {
                SettingsNavigationRow(
                    title = "Configuración de clima",
                    subtitle = "Temperatura, humedad y estación",
                    onClick = onNavigateToClimate
                )
            }

            SettingsSection(title = "Notificaciones") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Activar notificaciones",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Recordatorios globales de riego",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.notificationsEnabled,
                        onCheckedChange = viewModel::onNotificationsEnabledChange
                    )
                }
                HorizontalDivider()
                SettingsNavigationRow(
                    title = "Hora preferida",
                    subtitle = formatTime(uiState.reminderHour, uiState.reminderMinute),
                    onClick = viewModel::showTimePicker
                )
            }

            SettingsSection(title = "Datos") {
                SecondaryButton(
                    text = if (uiState.isLoadingUsage) "Cargando..." else "Ver resumen de uso",
                    onClick = viewModel::loadUsageSummary,
                    enabled = !uiState.isLoadingUsage
                )
                if (uiState.showUsageSummary && !uiState.isLoadingUsage) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Plantas registradas: ${uiState.totalPlants ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Riegos registrados: ${uiState.totalWaterings ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            SettingsSection(title = "Información") {
                Text(
                    text = "Versión 1.0",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "CropCare — Proyecto Final\nDesarrollado por el equipo CropCare:\n" +
                        "Infraestructura y Plantas · Riego y Configuración · Notificaciones y Catálogo",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
private fun SettingsNavigationRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

private fun formatTime(hour: Int, minute: Int): String =
    String.format(Locale("es", "ES"), "%02d:%02d", hour, minute)
