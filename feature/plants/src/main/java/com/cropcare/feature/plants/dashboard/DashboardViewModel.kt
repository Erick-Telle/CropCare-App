package com.cropcare.feature.plants.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.DashboardStats
import com.cropcare.core.domain.model.PlantWithStatus
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.usecase.GetAllPlantsWithStatusUseCase
import com.cropcare.core.domain.usecase.GetDashboardStatsUseCase
import com.cropcare.core.domain.usecase.GetSpeciesCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlantWithSpecies(
    val plantWithStatus: PlantWithStatus,
    val speciesName: String,
    val nextWateringText: String
)

data class DashboardUiState(
    val isLoading: Boolean = true,
    val todayPlants: List<PlantWithSpecies> = emptyList(),
    val upcomingPlants: List<PlantWithSpecies> = emptyList(),
    val isEmpty: Boolean = false,
    val stats: DashboardStats = DashboardStats()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAllPlantsWithStatusUseCase: GetAllPlantsWithStatusUseCase,
    private val getSpeciesCatalogUseCase: GetSpeciesCatalogUseCase,
    private val getDashboardStatsUseCase: GetDashboardStatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getAllPlantsWithStatusUseCase(),
                getSpeciesCatalogUseCase(),
                getDashboardStatsUseCase()
            ) { plantsWithStatus, speciesList, stats ->
                val speciesMap = speciesList.associateBy { it.id }
                val plantItems = plantsWithStatus.map { item ->
                    val species = speciesMap[item.plant.especieId]
                    PlantWithSpecies(
                        plantWithStatus = item,
                        speciesName = species?.nombreComun ?: "Especie desconocida",
                        nextWateringText = formatNextWateringText(item)
                    )
                }
                Triple(plantItems, stats, plantItems.isEmpty())
            }.collect { (plantItems, stats, isEmpty) ->
                val today = plantItems.filter {
                    it.plantWithStatus.status == WateringStatus.PENDIENTE ||
                        it.plantWithStatus.status == WateringStatus.ATRASADA
                }
                val upcoming = plantItems.filter {
                    it.plantWithStatus.status == WateringStatus.AL_DIA
                }
                _uiState.update {
                    DashboardUiState(
                        isLoading = false,
                        todayPlants = today,
                        upcomingPlants = upcoming,
                        isEmpty = isEmpty,
                        stats = stats
                    )
                }
            }
        }
    }

    private fun formatNextWateringText(item: PlantWithStatus): String = when {
        item.diasRestantes < 0 -> "Riego atrasado"
        item.diasRestantes == 0 -> "Riego hoy"
        item.diasRestantes == 1 -> "Próximo riego: mañana"
        else -> "Próximo riego: en ${item.diasRestantes} días"
    }
}
