package com.cropcare.feature.settings.climate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.domain.model.Season
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.components.PrimaryButton

@Composable
fun ClimateConfigScreen(
    onNavigateBack: () -> Unit,
    viewModel: ClimateConfigViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CropCareTopAppBar(
                title = "Configuración de clima",
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            LoadingView(Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Temperatura promedio: ${uiState.temperature.toInt()} °C",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Slider(
                    value = uiState.temperature,
                    onValueChange = viewModel::onTemperatureChange,
                    valueRange = 0f..45f,
                    steps = 44
                )

                Text(
                    text = "Humedad ambiental: ${uiState.humidity.toInt()} %",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Slider(
                    value = uiState.humidity,
                    onValueChange = viewModel::onHumidityChange,
                    valueRange = 0f..100f,
                    steps = 19
                )

                Text(
                    text = "Estación actual",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                SeasonSelector(
                    selected = uiState.season,
                    onSeasonSelected = viewModel::onSeasonChange
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = uiState.previewText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                if (uiState.saveSuccess) {
                    Text(
                        text = "Configuración guardada. Se recalcularon todas las plantas activas.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                PrimaryButton(
                    text = if (uiState.isSaving) "Guardando..." else "Guardar configuración",
                    onClick = viewModel::saveConfiguration,
                    enabled = !uiState.isSaving
                )
            }
        }
    }
}

@Composable
private fun SeasonSelector(
    selected: Season,
    onSeasonSelected: (Season) -> Unit
) {
    val seasons = listOf(
        Season.VERANO to "Verano",
        Season.OTONO to "Otoño",
        Season.INVIERNO to "Invierno",
        Season.PRIMAVERA to "Primavera"
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        seasons.chunked(2).forEach { row ->
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { (season, label) ->
                    FilterChip(
                        selected = selected == season,
                        onClick = { onSeasonSelected(season) },
                        label = { Text(label) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
