package com.cropcare.feature.plants.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.WateringStatus
import com.cropcare.core.domain.usecase.GetAllPlantsUseCase
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
    val plant: Plant,
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
    private val getAllPlantsUseCase: GetAllPlantsUseCase,
    private val getSpeciesCatalogUseCase: GetSpeciesCatalogUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getAllPlantsUseCase(),
                getSpeciesCatalogUseCase()
            ) { plants, speciesList ->
                val speciesMap = speciesList.associateBy { it.id }
                plants.map { plant ->
                    val species = speciesMap[plant.especieId]
                    PlantWithSpecies(
                        plant = plant,
                        speciesName = species?.nombreComun ?: "Especie desconocida",
                        // TODO: Reemplazar con cálculo real desde GetPlantWateringStatusUseCase (Dev 2)
                        nextWateringText = "Próximo riego: en ${plant.frecuenciaRiegoDias} días"
                    )
                }
            }.collect { plantItems ->
                // TODO: Filtrar "Hoy" con GetPlantsNeedingWaterTodayUseCase (Dev 2)
                val today = plantItems.filter {
                    it.plant.estadoRiego == WateringStatus.PENDIENTE ||
                        it.plant.estadoRiego == WateringStatus.ATRASADA
                }
                val upcoming = plantItems.filter {
                    it.plant.estadoRiego == WateringStatus.AL_DIA
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
}
