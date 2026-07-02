package com.cropcare.feature.plants.detail

import com.cropcare.core.ui.components.CareAdviceCard
import com.cropcare.core.ui.charts.LineAreaChart
import com.cropcare.core.ui.charts.WateringHeatmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cropcare.core.ui.components.ChartCard
import com.cropcare.core.ui.components.CircularWateringProgress
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.InputField
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.components.PrimaryButton
import com.cropcare.core.ui.components.StatusChip
import com.cropcare.feature.watering.quickwater.QuickWateringBottomSheet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantDetailScreen(
    onNavigateBack: () -> Unit,
    onEditPlant: (Long) -> Unit,
    onViewWateringHistory: (Long) -> Unit,
    onPlantDeleted: () -> Unit = onNavigateBack,
    viewModel: PlantDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.plantDeleted) {
        if (uiState.plantDeleted) {
            viewModel.consumePlantDeleted()
            onPlantDeleted()
        }
    }

    if (uiState.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = viewModel::dismissDeleteDialog,
            title = { Text("Eliminar planta") },
            text = {
                Text(
                    "Se borrará \"${uiState.plant?.nombre ?: "esta planta"}\" junto con " +
                        "su historial de riegos. Esta acción no se puede deshacer."
                )
            },
            confirmButton = {
                Button(
                    onClick = viewModel::confirmDeletePlant,
                    enabled = !uiState.isDeleting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(if (uiState.isDeleting) "Eliminando…" else "Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = viewModel::dismissDeleteDialog,
                    enabled = !uiState.isDeleting
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (uiState.showWateringSheet && uiState.plant != null) {
        QuickWateringBottomSheet(
            plantName = uiState.plant!!.nombre,
            suggestedWaterMl = uiState.plant!!.cantidadAguaMl,
            waterAmount = uiState.wateringAmount,
            onWaterAmountChange = viewModel::onWateringAmountChange,
            notes = uiState.wateringNotes,
            onNotesChange = viewModel::onWateringNotesChange,
            isSaving = uiState.isRegisteringWatering,
            onConfirm = viewModel::confirmWatering,
            onDismiss = viewModel::dismissWateringSheet
        )
    }

    Scaffold(
        topBar = {
            CropCareTopAppBar(
                title = uiState.plant?.nombre ?: "Detalle",
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.plant != null && uiState.species != null -> {
                val plant = uiState.plant!!
                val species = uiState.species!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        if (plant.fotoPath != null) {
                            AsyncImage(
                                model = plant.fotoPath,
                                contentDescription = plant.nombre,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFlorist,
                                    contentDescription = null,
                                    modifier = Modifier.height(80.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                        startY = 100f
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = plant.nombre,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = species.nombreComun,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        StatusChip(status = plant.estadoRiego)

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Próximo riego",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = uiState.nextWateringText,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Cantidad: ${plant.cantidadAguaMl} ml",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                CircularWateringProgress(
                                    progress = computeWateringProgress(plant, uiState.wateringRecords),
                                    status = plant.estadoRiego,
                                    centerText = progressLabel(uiState.nextWateringText),
                                    subText = "Progreso",
                                    size = 96.dp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        PrimaryButton(
                            text = "Marcar como regada",
                            onClick = viewModel::markAsWatered
                        )
                    }

                    val tabs = listOf("Historial", "Consejos", "Ajustes")
                    TabRow(selectedTabIndex = uiState.selectedTab) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = uiState.selectedTab == index,
                                onClick = { viewModel.onTabSelected(index) },
                                text = { Text(title) }
                            )
                        }
                    }

                    when (uiState.selectedTab) {
                        0 -> HistoryTab(
                            records = uiState.wateringRecords,
                            onViewFullHistory = { onViewWateringHistory(plant.id) }
                        )
                        1 -> TipsTab(species = species)
                        2 -> SettingsTab(
                            frequency = uiState.editFrequency,
                            waterAmount = uiState.editWaterAmount,
                            onFrequencyChange = viewModel::onEditFrequencyChange,
                            onWaterAmountChange = viewModel::onEditWaterAmountChange,
                            onSave = viewModel::saveWateringSettings,
                            onEditPlant = { onEditPlant(plant.id) },
                            onDeletePlant = viewModel::showDeleteConfirmation
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTab(
    records: List<com.cropcare.core.domain.model.WateringRecord>,
    onViewFullHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (records.isEmpty()) {
            Text(
                text = "Sin registros de riego",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onViewFullHistory) {
                Text("Ver historial completo")
            }
        } else {
            ChartCard(
                title = "Cantidad de agua",
                subtitle = "Historial de riegos registrados"
            ) {
                LineAreaChart(points = wateringLinePoints(records))
            }

            ChartCard(
                title = "Consistencia",
                subtitle = "Últimos 30 días"
            ) {
                WateringHeatmap(
                    days = 30,
                    wateredDays = wateringHeatmapDays(records)
                )
            }

            TextButton(onClick = onViewFullHistory) {
                Text("Ver historial completo")
            }

            records.take(5).forEach { record ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = formatDate(record.timestamp),
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
    }
}

@Composable
private fun TipsTab(species: com.cropcare.core.domain.model.Species) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CareAdviceCard(
            icon = Icons.Default.WbSunny,
            title = "Luz",
            description = species.consejosLuz
        )
        CareAdviceCard(
            icon = Icons.Default.WaterDrop,
            title = "Humedad",
            description = species.consejosHumedad
        )
        CareAdviceCard(
            icon = Icons.Default.Grass,
            title = "Abono",
            description = species.consejosAbono
        )
    }
}

private fun progressLabel(nextWateringText: String): String = when {
    nextWateringText.startsWith("Atrasado") -> "Atrasado"
    nextWateringText == "Hoy" -> "Hoy"
    nextWateringText == "Mañana" -> "Mañana"
    nextWateringText.startsWith("En ") -> {
        nextWateringText.substringAfter("En ").substringBefore(" días").trim() + "d"
    }
    else -> nextWateringText.take(8)
}

@Composable
private fun SettingsTab(
    frequency: String,
    waterAmount: String,
    onFrequencyChange: (String) -> Unit,
    onWaterAmountChange: (String) -> Unit,
    onSave: () -> Unit,
    onEditPlant: () -> Unit,
    onDeletePlant: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Ajustes de riego",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        InputField(
            value = frequency,
            onValueChange = onFrequencyChange,
            label = "Frecuencia (días)",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        InputField(
            value = waterAmount,
            onValueChange = onWaterAmountChange,
            label = "Cantidad de agua (ml)",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        PrimaryButton(text = "Guardar ajustes", onClick = onSave)

        PrimaryButton(text = "Editar planta", onClick = onEditPlant)

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Zona de peligro",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.error
        )
        Text(
            text = "Eliminar esta planta borrará también su historial de riegos y recordatorios.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Button(
            onClick = onDeletePlant,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(
                    text = "Eliminar planta",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "ES"))
    return formatter.format(Date(timestamp))
}
