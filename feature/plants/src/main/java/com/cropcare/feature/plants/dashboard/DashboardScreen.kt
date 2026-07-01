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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onAddPlant: () -> Unit,
    onPlantClick: (Long) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenSpeciesCatalog: () -> Unit = onOpenSettings,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val allPlants = uiState.todayPlants + uiState.upcomingPlants
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    MainTabShell(
        onAddPlant = onAddPlant,
        onOpenSettings = onOpenSettings,
        onOpenSpeciesCatalog = onOpenSpeciesCatalog,
        homeContent = {
            when {
                uiState.isLoading -> LoadingView()
                uiState.isEmpty -> EmptyStateView(
                    title = "Sin plantas aún",
                    message = "Agrega tu primera planta para comenzar a recibir recordatorios de riego personalizados.",
                    action = { PrimaryButton(text = "Agregar planta", onClick = onAddPlant) }
                )
                else -> PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        scope.launch {
                            isRefreshing = true
                            delay(800)
                            isRefreshing = false
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            CropCareTopAppBar(title = "CropCare", transparent = true)
                        }

                        item {
                            DashboardStatsSection(allPlants = allPlants)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

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
                                            modifier = Modifier.width(168.dp),
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
        },
        statsContent = {
            when {
                uiState.isLoading -> LoadingView()
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        CropCareTopAppBar(title = "Estadísticas", transparent = true)
                    }
                    item {
                        DashboardStatsSection(allPlants = allPlants)
                    }
                }
            }
        }
    )
}
