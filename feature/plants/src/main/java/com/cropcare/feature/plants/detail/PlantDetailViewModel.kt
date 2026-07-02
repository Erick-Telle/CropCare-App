package com.cropcare.feature.plants.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cropcare.core.domain.model.Plant
import com.cropcare.core.domain.model.Species
import com.cropcare.core.domain.model.WateringRecord
import com.cropcare.core.domain.usecase.CancelPlantNotificationUseCase
import com.cropcare.core.domain.usecase.DeletePlantUseCase
import com.cropcare.core.domain.usecase.GetNextWateringDateUseCase
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase
import com.cropcare.core.domain.usecase.GetPlantWateringStatusUseCase
import com.cropcare.core.domain.usecase.GetSpeciesByIdUseCase
import com.cropcare.core.domain.usecase.GetWateringHistoryUseCase
import com.cropcare.core.domain.usecase.RegisterWateringUseCase
import com.cropcare.core.domain.usecase.SavePlantUseCase
import com.cropcare.feature.plants.form.PlantPhotoStorage
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class PlantDetailUiState(
    val isLoading: Boolean = true,
    val plant: Plant? = null,
    val species: Species? = null,
    val wateringRecords: List<WateringRecord> = emptyList(),
    val selectedTab: Int = 0,
    val editFrequency: String = "",
    val editWaterAmount: String = "",
    val showWateringSheet: Boolean = false,
    val wateringAmount: String = "",
    val wateringNotes: String = "",
    val isRegisteringWatering: Boolean = false,
    val nextWateringText: String = "",
    val settingsSaved: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isDeleting: Boolean = false,
    val plantDeleted: Boolean = false
)

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlantByIdUseCase: GetPlantByIdUseCase,
    private val getSpeciesByIdUseCase: GetSpeciesByIdUseCase,
    private val getWateringHistoryUseCase: GetWateringHistoryUseCase,
    private val savePlantUseCase: SavePlantUseCase,
    private val registerWateringUseCase: RegisterWateringUseCase,
    private val getNextWateringDateUseCase: GetNextWateringDateUseCase,
    private val getPlantWateringStatusUseCase: GetPlantWateringStatusUseCase,
    private val deletePlantUseCase: DeletePlantUseCase,
    private val cancelPlantNotificationUseCase: CancelPlantNotificationUseCase
) : ViewModel() {

    private val plantId: Long = checkNotNull(savedStateHandle[PlantsRoutes.ARG_PLANT_ID])

    private val _uiState = MutableStateFlow(PlantDetailUiState())
    val uiState: StateFlow<PlantDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getPlantByIdUseCase(plantId).filterNotNull(),
                getWateringHistoryUseCase(plantId)
            ) { plant, records -> plant to records }
                .flatMapLatest { (plant, records) ->
                    getSpeciesByIdUseCase(plant.especieId).filterNotNull()
                        .map { species -> Triple(plant, species, records) }
                }
                .collect { (plant, species, records) ->
                    val lastWatering = records.firstOrNull()?.timestamp ?: plant.fechaCreacion
                    val proximaFecha = getNextWateringDateUseCase(lastWatering, plant.frecuenciaRiegoDias)
                    val diasRestantes = getPlantWateringStatusUseCase.calculateDaysRemaining(proximaFecha)
                    val nextText = formatNextWateringText(diasRestantes, proximaFecha)

                    _uiState.update { current ->
                        PlantDetailUiState(
                            isLoading = false,
                            plant = plant,
                            species = species,
                            wateringRecords = records,
                            selectedTab = current.selectedTab,
                            editFrequency = plant.frecuenciaRiegoDias.toString(),
                            editWaterAmount = plant.cantidadAguaMl.toString(),
                            showWateringSheet = current.showWateringSheet,
                            wateringAmount = if (current.wateringAmount.isEmpty()) {
                                plant.cantidadAguaMl.toString()
                            } else {
                                current.wateringAmount
                            },
                            wateringNotes = current.wateringNotes,
                            isRegisteringWatering = current.isRegisteringWatering,
                            nextWateringText = nextText,
                            settingsSaved = current.settingsSaved,
                            showDeleteDialog = current.showDeleteDialog,
                            isDeleting = current.isDeleting,
                            plantDeleted = current.plantDeleted
                        )
                    }
                }
        }
    }

    fun showDeleteConfirmation() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun confirmDeletePlant() {
        val plant = _uiState.value.plant ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true) }
            cancelPlantNotificationUseCase(plant.id)
            PlantPhotoStorage.deletePhoto(plant.fotoPath)
            deletePlantUseCase(plant)
            _uiState.update {
                it.copy(
                    isDeleting = false,
                    showDeleteDialog = false,
                    plantDeleted = true
                )
            }
        }
    }

    fun consumePlantDeleted() {
        _uiState.update { it.copy(plantDeleted = false) }
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
        val plant = _uiState.value.plant ?: return
        _uiState.update {
            it.copy(
                showWateringSheet = true,
                wateringAmount = plant.cantidadAguaMl.toString(),
                wateringNotes = ""
            )
        }
    }

    fun dismissWateringSheet() {
        _uiState.update {
            it.copy(
                showWateringSheet = false,
                wateringNotes = "",
                isRegisteringWatering = false
            )
        }
    }

    fun onWateringAmountChange(value: String) {
        _uiState.update { it.copy(wateringAmount = value) }
    }

    fun onWateringNotesChange(value: String) {
        _uiState.update { it.copy(wateringNotes = value) }
    }

    fun confirmWatering() {
        val state = _uiState.value
        val amount = state.wateringAmount.toIntOrNull() ?: return
        if (amount <= 0) return

        viewModelScope.launch {
            _uiState.update { it.copy(isRegisteringWatering = true) }
            registerWateringUseCase(
                plantId = plantId,
                cantidadAguaMl = amount,
                notas = state.wateringNotes.ifBlank { null }
            )
            _uiState.update {
                it.copy(
                    showWateringSheet = false,
                    wateringNotes = "",
                    wateringAmount = "",
                    isRegisteringWatering = false
                )
            }
        }
    }

    private fun formatNextWateringText(diasRestantes: Int, proximaFecha: Long): String = when {
        diasRestantes < 0 -> "Atrasado desde ${formatDate(proximaFecha)}"
        diasRestantes == 0 -> "Hoy"
        diasRestantes == 1 -> "Mañana"
        else -> "En $diasRestantes días (${formatDate(proximaFecha)})"
    }

    private fun formatDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("dd MMM", Locale("es", "ES"))
        return formatter.format(Date(timestamp))
    }
}
