package com.cropcare.feature.catalog.care

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.cropcare.core.ui.components.CareAdviceCard
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.EmptyStateView
import com.cropcare.core.ui.components.LoadingView

@Composable
fun CareAdviceScreen(
    onNavigateBack: () -> Unit,
    viewModel: CareAdviceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CropCareTopAppBar(
                title = "Consejos de cuidado",
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingView(Modifier.padding(padding))
            uiState.errorMessage != null -> EmptyStateView(
                title = "Error",
                message = uiState.errorMessage!!,
                modifier = Modifier.padding(padding)
            )
            uiState.careAdvice != null -> {
                val advice = uiState.careAdvice!!
                val species = advice.species

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = species.nombreComun,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = species.nombreCientifico,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    CareAdviceCard(
                        icon = Icons.Default.WbSunny,
                        title = "Luz solar",
                        description = species.consejosLuz,
                        level = advice.luzLevel,
                        levelIcons = listOf(
                            Icons.Default.WbSunny,
                            Icons.Default.WbSunny,
                            Icons.Default.WbSunny
                        )
                    )

                    CareAdviceCard(
                        icon = Icons.Default.Opacity,
                        title = "Humedad",
                        description = species.consejosHumedad,
                        level = advice.humedadLevel,
                        levelIcons = listOf(
                            Icons.Default.Opacity,
                            Icons.Default.Opacity,
                            Icons.Default.Opacity
                        )
                    )

                    CareAdviceCard(
                        icon = Icons.Default.Eco,
                        title = "Abono y fertilización",
                        description = species.consejosAbono
                    )

                    WateringDataSection(
                        frecuenciaBaseDias = species.frecuenciaBaseDias,
                        cantidadBaseAguaMl = species.cantidadBaseAguaMl
                    )
                }
            }
        }
    }
}

@Composable
private fun WateringDataSection(
    frecuenciaBaseDias: Int,
    cantidadBaseAguaMl: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Datos de riego",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Frecuencia base: cada $frecuenciaBaseDias días",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Cantidad base: $cantidadBaseAguaMl ml",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Estos valores se ajustan según tu configuración de clima local",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
