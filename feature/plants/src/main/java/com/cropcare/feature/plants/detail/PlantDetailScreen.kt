package com.cropcare.feature.plants.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.InputField
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.components.PrimaryButton
import com.cropcare.core.ui.components.StatusChip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantDetailScreen(
    onNavigateBack: () -> Unit,
    onEditPlant: (Long) -> Unit,
    viewModel: PlantDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.showWateredPlaceholder) {
        AlertDialog(
            onDismissRequest = viewModel::dismissWateredPlaceholder,
            title = { Text("Riego registrado") },
            text = {
                Text(
                    // TODO: Reemplazar placeholder cuando MarkPlantAsWateredUseCase esté disponible (Dev 2)
                    "Funcionalidad pendiente de integración con feature:watering."
                )
            },
            confirmButton = {
                TextButton(onClick = viewModel::dismissWateredPlaceholder) {
                    Text("Aceptar")
                }
            }
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
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Próximo riego",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    // TODO: Fecha real desde GetNextWateringDateUseCase (Dev 2)
                                    text = "En ${plant.frecuenciaRiegoDias} días",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Cantidad: ${plant.cantidadAguaMl} ml",
                                    style = MaterialTheme.typography.bodyMedium
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
                        0 -> HistoryTab(records = uiState.wateringRecords)
                        1 -> TipsTab(species = species)
                        2 -> SettingsTab(
                            frequency = uiState.editFrequency,
                            waterAmount = uiState.editWaterAmount,
                            onFrequencyChange = viewModel::onEditFrequencyChange,
                            onWaterAmountChange = viewModel::onEditWaterAmountChange,
                            onSave = viewModel::saveWateringSettings,
                            onEditPlant = { onEditPlant(plant.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTab(records: List<com.cropcare.core.domain.model.WateringRecord>) {
    if (records.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sin registros de riego",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                // TODO: Datos completos desde GetWateringHistoryUseCase con filtros (Dev 2)
                text = "El historial se completará con feature:watering",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(records, key = { it.id }) { record ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
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
        TipCard(title = "Luz", content = species.consejosLuz)
        TipCard(title = "Humedad", content = species.consejosHumedad)
        TipCard(title = "Abono", content = species.consejosAbono)
    }
}

@Composable
private fun TipCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun SettingsTab(
    frequency: String,
    waterAmount: String,
    onFrequencyChange: (String) -> Unit,
    onWaterAmountChange: (String) -> Unit,
    onSave: () -> Unit,
    onEditPlant: () -> Unit
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
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "ES"))
    return formatter.format(Date(timestamp))
}
