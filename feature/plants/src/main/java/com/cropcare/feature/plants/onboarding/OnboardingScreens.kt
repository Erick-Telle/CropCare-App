package com.cropcare.feature.plants.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.domain.model.Season
import com.cropcare.core.ui.components.InputField
import com.cropcare.core.ui.components.PrimaryButton

@Composable
fun OnboardingWelcomeScreen(
    onContinue: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalFlorist,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "¡Bienvenido a CropCare!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tu asistente personal para el cuidado de plantas domésticas. " +
                    "Configura tu clima local y comienza a registrar tus plantas.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(48.dp))
            PrimaryButton(
                text = "Comenzar",
                onClick = onContinue
            )
        }
    }
}

@Composable
fun OnboardingClimateScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.climateState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Configura tu clima",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Estos datos nos ayudan a calcular la frecuencia de riego ideal para tus plantas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                value = state.temperatura,
                onValueChange = viewModel::onTemperaturaChange,
                label = "Temperatura promedio (°C)",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            InputField(
                value = state.humedad,
                onValueChange = viewModel::onHumedadChange,
                label = "Humedad ambiental (%)",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Estación actual",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Season.entries.forEach { season ->
                FilterChip(
                    selected = state.estacion == season,
                    onClick = { viewModel.onEstacionChange(season) },
                    label = { Text(seasonLabel(season)) },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }

            if (state.errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            PrimaryButton(
                text = if (state.isSaving) "Guardando..." else "Finalizar configuración",
                onClick = { viewModel.saveClimateAndComplete(onComplete) },
                enabled = !state.isSaving
            )
        }
    }
}

private fun seasonLabel(season: Season): String = when (season) {
    Season.VERANO -> "Verano"
    Season.OTONO -> "Otoño"
    Season.INVIERNO -> "Invierno"
    Season.PRIMAVERA -> "Primavera"
}
