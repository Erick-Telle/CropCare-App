package com.cropcare.feature.plants.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.PlantWithStatus
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.usecase.GetAllPlantsWithStatusUseCase
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
    val isEmpty: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAllPlantsWithStatusUseCase: GetAllPlantsWithStatusUseCase,
    private val getSpeciesCatalogUseCase: GetSpeciesCatalogUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getAllPlantsWithStatusUseCase(),
                getSpeciesCatalogUseCase()
            ) { plantsWithStatus, speciesList ->
                val speciesMap = speciesList.associateBy { it.id }
                plantsWithStatus.map { item ->
                    val species = speciesMap[item.plant.especieId]
                    PlantWithSpecies(
                        plantWithStatus = item,
                        speciesName = species?.nombreComun ?: "Especie desconocida",
                        nextWateringText = formatNextWateringText(item)
                    )
                }
            }.collect { plantItems ->
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
                        isEmpty = plantItems.isEmpty()
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
