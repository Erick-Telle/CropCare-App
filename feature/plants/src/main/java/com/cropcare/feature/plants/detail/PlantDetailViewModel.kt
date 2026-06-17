package com.cropcare.feature.plants.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase
import com.cropcare.core.domain.usecase.GetSpeciesByIdUseCase
import com.cropcare.core.domain.usecase.GetWateringRecordsUseCase
import com.cropcare.core.domain.usecase.SavePlantUseCase
import com.cropcare.feature.plants.navigation.PlantsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlantDetailUiState(
    val isLoading: Boolean = true,
    val plant: Plant? = null,
    val species: Species? = null,
    val wateringRecords: List<WateringRecord> = emptyList(),
    val selectedTab: Int = 0,
    val editFrequency: String = "",
    val editWaterAmount: String = "",
    val showWateredPlaceholder: Boolean = false,
    val settingsSaved: Boolean = false
)

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlantByIdUseCase: GetPlantByIdUseCase,
    private val getSpeciesByIdUseCase: GetSpeciesByIdUseCase,
    private val getWateringRecordsUseCase: GetWateringRecordsUseCase,
    private val savePlantUseCase: SavePlantUseCase
) : ViewModel() {

    private val plantId: Long = checkNotNull(savedStateHandle[PlantsRoutes.ARG_PLANT_ID])

    private val _uiState = MutableStateFlow(PlantDetailUiState())
    val uiState: StateFlow<PlantDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getPlantByIdUseCase(plantId).filterNotNull(),
                getWateringRecordsUseCase(plantId)
            ) { plant, records -> plant to records }
                .flatMapLatest { (plant, records) ->
                    getSpeciesByIdUseCase(plant.especieId).filterNotNull()
                        .map { species -> Triple(plant, species, records) }
                }
                .collect { (plant, species, records) ->
                    _uiState.update { current ->
                        PlantDetailUiState(
                            isLoading = false,
                            plant = plant,
                            species = species,
                            wateringRecords = records,
                            selectedTab = current.selectedTab,
                            editFrequency = plant.frecuenciaRiegoDias.toString(),
                            editWaterAmount = plant.cantidadAguaMl.toString(),
                            showWateredPlaceholder = current.showWateredPlaceholder
                        )
                    }
                }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun onEditFrequencyChange(value: String) {
        _uiState.update { it.copy(editFrequency = value) }
    }

    fun onEditWaterAmountChange(value: String) {
        _uiState.update { it.copy(editWaterAmount = value) }
    }

    fun saveWateringSettings() {
        val state = _uiState.value
        val plant = state.plant ?: return
        val frequency = state.editFrequency.toIntOrNull() ?: return
        val water = state.editWaterAmount.toIntOrNull() ?: return
        if (frequency <= 0 || water <= 0) return

        viewModelScope.launch {
            savePlantUseCase(
                plant.copy(
                    frecuenciaRiegoDias = frequency,
                    cantidadAguaMl = water
                )
            )
            _uiState.update { it.copy(settingsSaved = true) }
        }
    }

    fun markAsWatered() {
        // TODO: Conectar con MarkPlantAsWateredUseCase (Dev 2 - feature:watering)
        _uiState.update { it.copy(showWateredPlaceholder = true) }
    }

    fun dismissWateredPlaceholder() {
        _uiState.update { it.copy(showWateredPlaceholder = false) }
    }
}
