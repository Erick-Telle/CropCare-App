package com.cropcare.feature.notifications.permission

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun ExactAlarmPermissionEffect(
    snackbarHostState: SnackbarHostState
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return

    val context = LocalContext.current
    var permissionChecked by remember { mutableStateOf(false) }
    var showDeniedMessage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!permissionChecked && !canScheduleExactAlarms(context)) {
            permissionChecked = true
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = android.net.Uri.parse("package:${context.packageName}")
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                showDeniedMessage = true
            }
        }
    }

    LaunchedEffect(showDeniedMessage) {
        if (showDeniedMessage) {
            snackbarHostState.showSnackbar(
                "Para recordatorios precisos de riego, activa alarmas exactas en Ajustes del sistema."
            )
            showDeniedMessage = false
        }
    }
}

private fun canScheduleExactAlarms(context: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
    val alarmManager = context.getSystemService(AlarmManager::class.java)
    return alarmManager.canScheduleExactAlarms()
}
