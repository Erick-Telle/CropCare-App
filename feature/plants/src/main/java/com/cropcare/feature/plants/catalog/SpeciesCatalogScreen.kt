package com.cropcare.feature.plants.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cropcare.core.domain.model.SpeciesCatalogSyncState
import com.cropcare.core.ui.components.CropCareTopAppBar
import com.cropcare.core.ui.components.EmptyStateView
import com.cropcare.core.ui.components.InputField
import com.cropcare.core.ui.components.LoadingView
import com.cropcare.core.ui.modifier.pressScale
import com.cropcare.core.ui.theme.AccentEmerald
import kotlinx.coroutines.delay

@Composable
fun SpeciesCatalogScreen(
    onNavigateBack: () -> Unit,
    onSpeciesSelected: (Long, String) -> Unit,
    browseMode: Boolean = false,
    viewModel: SpeciesCatalogViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val title = if (browseMode) "Consejos por especie" else "Catálogo de especies"
    var showUpdatedBanner by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.syncState) {
        if (uiState.syncState == SpeciesCatalogSyncState.ACTUALIZADO) {
            showUpdatedBanner = true
            delay(2500)
            showUpdatedBanner = false
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CropCareTopAppBar(
                title = title,
                showBackButton = true,
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            InputField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = "Buscar especie",
                placeholder = "Nombre común o científico"
            )

            Spacer(modifier = Modifier.height(12.dp))

            SpeciesSyncBanner(
                syncState = uiState.syncState,
                showUpdatedBanner = showUpdatedBanner
            )

            if (SpeciesSyncBannerVisible(uiState.syncState, showUpdatedBanner)) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            when {
                uiState.isLoading -> LoadingView()
                uiState.species.isEmpty() &&
                    uiState.syncState == SpeciesCatalogSyncState.USANDO_CACHE_LOCAL -> {
                    EmptyStateView(
                        title = "Sin conexión",
                        message = "No hay especies guardadas en el dispositivo. Conéctate a internet para descargar el catálogo.",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                uiState.species.isEmpty() -> {
                    EmptyStateView(
                        title = "Sin resultados",
                        message = "No encontramos especies con ese nombre. Prueba con otro término.",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.species, key = { it.id }) { species ->
                            SpeciesGridCard(
                                commonName = species.nombreComun,
                                scientificName = species.nombreCientifico,
                                wateringInfo = "Cada ${species.frecuenciaBaseDias} d · ${species.cantidadBaseAguaMl} ml",
                                onClick = { onSpeciesSelected(species.id, species.nombreComun) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpeciesSyncBanner(
    syncState: SpeciesCatalogSyncState,
    showUpdatedBanner: Boolean
) {
    if (!SpeciesSyncBannerVisible(syncState, showUpdatedBanner)) return

    val message = when {
        showUpdatedBanner -> "Catálogo actualizado"
        syncState == SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR ->
            "Conectando con el servidor…"
        syncState == SpeciesCatalogSyncState.USANDO_CACHE_LOCAL ->
            "Sin conexión — mostrando catálogo guardado"
        else -> return
    }

    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(
                width = 1.dp,
                color = AccentEmerald.copy(alpha = 0.35f),
                shape = shape
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when {
            syncState == SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR && !showUpdatedBanner -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = AccentEmerald
                )
            }
            syncState == SpeciesCatalogSyncState.USANDO_CACHE_LOCAL -> {
                Icon(
                    imageVector = Icons.Default.CloudOff,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
            showUpdatedBanner -> {
                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = null,
                    tint = AccentEmerald,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start
        )
    }
}

private fun SpeciesSyncBannerVisible(
    syncState: SpeciesCatalogSyncState,
    showUpdatedBanner: Boolean
): Boolean = showUpdatedBanner ||
    syncState == SpeciesCatalogSyncState.CARGANDO_DESDE_SERVIDOR ||
    syncState == SpeciesCatalogSyncState.USANDO_CACHE_LOCAL

@Composable
private fun SpeciesGridCard(
    commonName: String,
    scientificName: String,
    wateringInfo: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .pressScale(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = shape
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalFlorist,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = commonName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = scientificName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = wateringInfo,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
