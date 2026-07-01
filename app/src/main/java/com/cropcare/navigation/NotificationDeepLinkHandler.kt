package com.cropcare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.cropcare.feature.notifications.NotificationConstants
import com.cropcare.feature.plants.navigation.PlantsRoutes

@Composable
fun NotificationDeepLinkHandler(navController: NavHostController) {
    val context = LocalContext.current
    val plantId = (context as? android.app.Activity)?.intent
        ?.getLongExtra(NotificationConstants.EXTRA_PLANT_ID, -1L)
        ?: -1L

    LaunchedEffect(plantId) {
        if (plantId != -1L) {
            navController.navigate(PlantsRoutes.plantDetailRoute(plantId)) {
                launchSingleTop = true
            }
            (context as? android.app.Activity)?.intent?.removeExtra(
                NotificationConstants.EXTRA_PLANT_ID
            )
        }
    }
}
