package com.cropcare.feature.plants.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cropcare.core.ui.components.BottomNavConfig
import com.cropcare.core.ui.components.BottomNavItem
import com.cropcare.core.ui.components.CropCareBottomNavigationBar

@Composable
fun MainTabShell(
    onAddPlant: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenSpeciesCatalog: () -> Unit,
    homeContent: @Composable () -> Unit,
    statsContent: @Composable () -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(BottomNavItem.HOME) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            CropCareBottomNavigationBar(
                config = BottomNavConfig(
                    selected = selectedTab,
                    onItemSelected = { item ->
                        when (item) {
                            BottomNavItem.ADD -> onAddPlant()
                            BottomNavItem.SETTINGS -> onOpenSettings()
                            BottomNavItem.TIPS -> onOpenSpeciesCatalog()
                            else -> selectedTab = item
                        }
                    }
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedTab) {
                BottomNavItem.HOME -> homeContent()
                BottomNavItem.STATS -> statsContent()
                else -> homeContent()
            }
        }
    }
}
