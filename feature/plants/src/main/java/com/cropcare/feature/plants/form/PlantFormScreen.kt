package com.cropcare.feature.plants.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.domain.model.PlantLocation
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.InputField
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.components.PrimaryButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlantFormScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSpeciesCatalog: () -> Unit,
    onSaveSuccess: () -> Unit,
    onSpeciesSelected: (Long, String) -> Unit,
    viewModel: PlantFormViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            viewModel.consumeSaveSuccess()
            onSaveSuccess()
        }
    }

    LaunchedEffect(Unit) {
        // Species selection is handled via callback from navigation
    }

    Scaffold(
        topBar = {
            CropCareTopAppBar(
                title = if (state.plantId > 0) "Editar planta" else "Agregar planta",
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        if (state.isLoading) {
            LoadingView(Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                InputField(
                    value = state.nombre,
                    onValueChange = viewModel::onNombreChange,
                    label = "Nombre de la planta",
                    isError = state.nombreError != null,
                    errorMessage = state.nombreError
                )

                Box(modifier = Modifier.clickable { onNavigateToSpeciesCatalog() }) {
                    InputField(
                        value = state.especieNombre.ifEmpty { "Seleccionar especie" },
                        onValueChange = {},
                        label = "Especie",
                        readOnly = true,
                        isError = state.especieError != null,
                        errorMessage = state.especieError,
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    )
                }

                Text(
                    text = "Ubicación",
                    style = MaterialTheme.typography.titleMedium
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PlantLocation.entries.forEach { location ->
                        FilterChip(
                            selected = state.ubicacion == location,
                            onClick = { viewModel.onUbicacionChange(location) },
                            label = { Text(locationLabel(location)) }
                        )
                    }
                }

                Text(
                    text = "Frecuencia y cantidad de agua",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Valores calculados según especie y clima. Puedes personalizarlos.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                InputField(
                    value = state.frecuenciaRiegoDias,
                    onValueChange = viewModel::onFrecuenciaChange,
                    label = "Frecuencia de riego (días)",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                InputField(
                    value = state.cantidadAguaMl,
                    onValueChange = viewModel::onCantidadAguaChange,
                    label = "Cantidad de agua (ml)",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                PrimaryButton(
                    text = if (state.isSaving) "Guardando..." else "Guardar planta",
                    onClick = viewModel::savePlant,
                    enabled = !state.isSaving
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

fun locationLabel(location: PlantLocation): String = when (location) {
    PlantLocation.VENTANA -> "Ventana"
    PlantLocation.INTERIOR -> "Interior"
    PlantLocation.EXTERIOR -> "Exterior"
    PlantLocation.BALCON -> "Balcón"
}
