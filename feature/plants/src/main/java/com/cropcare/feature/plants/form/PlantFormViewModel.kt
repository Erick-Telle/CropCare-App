package com.cropcare.feature.plants.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.PlantLocation
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.usecase.CalculateWateringParamsUseCase
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase
import com.cropcare.core.domain.usecase.GetSpeciesByIdUseCase
import com.cropcare.core.domain.usecase.SavePlantUseCase
import com.cropcare.feature.plants.navigation.PlantsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlantFormUiState(
    val plantId: Long = 0,
    val nombre: String = "",
    val ubicacion: PlantLocation = PlantLocation.INTERIOR,
    val fotoPath: String? = null,
    val especieId: Long? = null,
    val especieNombre: String = "",
    val frecuenciaRiegoDias: String = "7",
    val cantidadAguaMl: String = "200",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val nombreError: String? = null,
    val especieError: String? = null,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class PlantFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val savePlantUseCase: SavePlantUseCase,
    private val getPlantByIdUseCase: GetPlantByIdUseCase,
    private val getSpeciesByIdUseCase: GetSpeciesByIdUseCase,
    private val calculateWateringParamsUseCase: CalculateWateringParamsUseCase
) : ViewModel() {

    private val editPlantId: Long? = savedStateHandle.get<Long>(PlantsRoutes.ARG_PLANT_ID)

    private val _uiState = MutableStateFlow(PlantFormUiState())
    val uiState: StateFlow<PlantFormUiState> = _uiState.asStateFlow()

    init {
        editPlantId?.let { id ->
            if (id > 0) loadPlant(id)
        }
    }

    private fun loadPlant(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val plant = getPlantByIdUseCase(id).filterNotNull().first()
            val species = getSpeciesByIdUseCase(plant.especieId).filterNotNull().first()
            _uiState.update {
                PlantFormUiState(
                    plantId = plant.id,
                    nombre = plant.nombre,
                    ubicacion = plant.ubicacion,
                    fotoPath = plant.fotoPath,
                    especieId = plant.especieId,
                    especieNombre = species.nombreComun,
                    frecuenciaRiegoDias = plant.frecuenciaRiegoDias.toString(),
                    cantidadAguaMl = plant.cantidadAguaMl.toString(),
                    isLoading = false
                )
            }
        }
    }

    fun onNombreChange(value: String) {
        _uiState.update { it.copy(nombre = value, nombreError = null) }
    }

    fun onUbicacionChange(location: PlantLocation) {
        _uiState.update { it.copy(ubicacion = location) }
    }

    fun onFotoPathChange(path: String?) {
        _uiState.update { it.copy(fotoPath = path) }
    }

    fun onFrecuenciaChange(value: String) {
        _uiState.update { it.copy(frecuenciaRiegoDias = value) }
    }

    fun onCantidadAguaChange(value: String) {
        _uiState.update { it.copy(cantidadAguaMl = value) }
    }

    fun onSpeciesSelected(speciesId: Long, speciesName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    especieId = speciesId,
                    especieNombre = speciesName,
                    especieError = null
                )
            }
            val (frequency, water) = calculateWateringParamsUseCase(speciesId)
            _uiState.update {
                it.copy(
                    frecuenciaRiegoDias = frequency.toString(),
                    cantidadAguaMl = water.toString()
                )
            }
        }
    }

    fun savePlant() {
        val state = _uiState.value
        var hasError = false

        if (state.nombre.isBlank()) {
            _uiState.update { it.copy(nombreError = "El nombre es obligatorio") }
            hasError = true
        }
        if (state.especieId == null) {
            _uiState.update { it.copy(especieError = "Selecciona una especie") }
            hasError = true
        }
        val frequency = state.frecuenciaRiegoDias.toIntOrNull()
        val water = state.cantidadAguaMl.toIntOrNull()
        if (frequency == null || frequency <= 0) {
            hasError = true
        }
        if (water == null || water <= 0) {
            hasError = true
        }
        if (hasError) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            savePlantUseCase(
                Plant(
                    id = state.plantId,
                    nombre = state.nombre.trim(),
                    especieId = state.especieId!!,
                    ubicacion = state.ubicacion,
                    fotoPath = state.fotoPath,
                    frecuenciaRiegoDias = frequency!!,
                    cantidadAguaMl = water!!
                )
            )
            _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
        }
    }

    fun consumeSaveSuccess() {
        _uiState.update { it.copy(saveSuccess = false) }
    }
}
