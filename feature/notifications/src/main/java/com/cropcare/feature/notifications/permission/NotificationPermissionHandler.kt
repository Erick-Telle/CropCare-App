package com.cropcare.feature.notifications.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.cropcare.feature.notifications.NotificationChannels

@Composable
fun NotificationPermissionEffect(
    snackbarHostState: SnackbarHostState
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val context = LocalContext.current
    var permissionRequested by remember { mutableStateOf(false) }
    var showDeniedMessage by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted && permissionRequested) {
            if (!NotificationChannels.areNotificationsEnabled(context)) {
                showDeniedMessage = true
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!NotificationChannels.areNotificationsEnabled(context) && !permissionRequested) {
            permissionRequested = true
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(showDeniedMessage) {
        if (showDeniedMessage) {
            snackbarHostState.showSnackbar(
                "Las notificaciones son necesarias para los recordatorios de riego. " +
                    "Puedes activarlas en Ajustes del sistema."
            )
            showDeniedMessage = false
        }
    }
}
