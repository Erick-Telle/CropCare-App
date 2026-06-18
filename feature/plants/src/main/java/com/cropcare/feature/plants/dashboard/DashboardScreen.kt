package com.cropcare.feature.plants.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.EmptyStateView
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.components.PlantCard
import com.cropcare.core.ui.components.PlantListItem
import com.cropcare.core.ui.components.PrimaryButton

@Composable
fun DashboardScreen(
    onAddPlant: () -> Unit,
    onPlantClick: (Long) -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CropCareTopAppBar(
                title = "CropCare",
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPlant,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar planta")
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.isEmpty -> EmptyStateView(
                modifier = Modifier.padding(padding),
                title = "Sin plantas aún",
                message = "Agrega tu primera planta para comenzar a recibir recordatorios de riego personalizados.",
                action = {
                    PrimaryButton(text = "Agregar planta", onClick = onAddPlant)
                }
            )
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.todayPlants.isNotEmpty()) {
                    item {
                        Text(
                            text = "Hoy",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(uiState.todayPlants, key = { it.plantWithStatus.plant.id }) { item ->
                                PlantCard(
                                    plantName = item.plantWithStatus.plant.nombre,
                                    speciesName = item.speciesName,
                                    status = item.plantWithStatus.status,
                                    photoPath = item.plantWithStatus.plant.fotoPath,
                                    modifier = Modifier.width(160.dp),
                                    onClick = { onPlantClick(item.plantWithStatus.plant.id) }
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Próximamente",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (uiState.upcomingPlants.isEmpty() && uiState.todayPlants.isNotEmpty()) {
                    item {
                        Text(
                            text = "No hay más plantas programadas próximamente.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                items(uiState.upcomingPlants, key = { it.plantWithStatus.plant.id }) { item ->
                    PlantListItem(
                        plantName = item.plantWithStatus.plant.nombre,
                        speciesName = item.speciesName,
                        status = item.plantWithStatus.status,
                        nextWateringText = item.nextWateringText,
                        onClick = { onPlantClick(item.plantWithStatus.plant.id) }
                    )
                }

                if (uiState.todayPlants.isEmpty() && uiState.upcomingPlants.isNotEmpty()) {
                    item {
                        Text(
                            text = "Ninguna planta necesita riego hoy.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
